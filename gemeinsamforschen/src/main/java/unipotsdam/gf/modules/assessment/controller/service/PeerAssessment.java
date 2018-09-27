package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.PeerRating;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentAndQuiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.model.cheatCheckerMethods;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeerAssessment implements IPeerAssessment {

    @Inject
    private Management management;

    @Override
    public void finalizeAssessment(String projectName){
        cheatCheckerMethods method = new AssessmentDBCommunication().getAssessmentMethod(projectName);
        Map<StudentIdentifier, Double> grading = calculateAssessment(projectName, method);
        new AssessmentDBCommunication().writeGradesToDB(grading);
    }

    @Override//returns one quiz
    public Quiz getQuiz(String projectName, String quizId, String author) {
        return new QuizDBCommunication().getQuizByProjectQuizId(projectName, quizId, author);
    }

    @Override //returns all quizzes in the course
    public ArrayList<Quiz> getQuiz(String projectName) {
        return new QuizDBCommunication().getQuizByProjectId(projectName);
    }

    @Override //returns all quizzes in the course
    public ArrayList<Quiz> getQuiz(String projectName, String author) {
        return new QuizDBCommunication().getQuizByProjectIdAuthor(projectName, author);
    }

    @Override
    public Map<StudentIdentifier, Double> getAssessmentForProject(String projectName) {
        return null;
    }

    @Override
    public Double getAssessmentForStudent(StudentIdentifier student) {
        return new AssessmentDBCommunication().getGradesFromDB(student);
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        new QuizDBCommunication().createQuiz(studentAndQuiz.getQuiz(), studentAndQuiz.getStudentIdentifier().getUserEmail(), studentAndQuiz.getStudentIdentifier().getProjectName());
    }

    @Override
    public void deleteQuiz(String quizId) {
        new QuizDBCommunication().deleteQuiz(quizId);
    }

    @Override
    public String whatToRate(StudentIdentifier student) {
        Integer groupId = new AssessmentDBCommunication().getGroupByStudent(student);
        ArrayList<String> groupMembers = new AssessmentDBCommunication().getStudentsByGroupAndProject(groupId, student.getProjectName());
        for (String peer : groupMembers) {
            if (!peer.equals(student.getUserEmail())) {
                StudentIdentifier groupMember = new StudentIdentifier(student.getProjectName(), peer);
                if (!new AssessmentDBCommunication().getWorkRating(groupMember, student.getUserEmail())) {
                    return "workRating";
                }
            }
        }
        ArrayList<Integer> answers = new AssessmentDBCommunication().getAnsweredQuizzes(student);
        if (answers == null) {
            return "quiz";
        }
        Integer groupToRate = new AssessmentDBCommunication().getWhichGroupToRate(student);
        if (!new AssessmentDBCommunication().getContributionRating(groupToRate, student.getUserEmail())) {
            return "contributionRating";
        }
        return "done";
    }

    @Override
    public Map<StudentIdentifier, ConstraintsMessages> allAssessmentsDone(String projectName) {
        Map<StudentIdentifier, ConstraintsMessages> result;
        result = new AssessmentDBCommunication().missingAssessments(projectName);
        return result;
    }

    @Override
    public void assignMissingAssessmentTasks(Project project) {

    }
    @Override
    public Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance) {
        Map<StudentIdentifier, Double> quizMean = new HashMap<>(quizGrade(totalPerformance));
        Map<StudentIdentifier, Map<String, Double>> workRating = new HashMap<>();
        Map<StudentIdentifier, Map<String, Double>> contributionRating = new HashMap<>();
        for (Performance performance : totalPerformance) {
            workRating.put(performance.getStudentIdentifier(), performance.getWorkRating());
            contributionRating.put(performance.getStudentIdentifier(), performance.getContributionRating());
        }
        Map<StudentIdentifier, Double> workRateMean = new HashMap<>(mapToGrade(workRating));
        Map<StudentIdentifier, Double> contributionMean = new HashMap<>(mapToGrade(contributionRating));
        Map<StudentIdentifier, Double> result = new HashMap<>();
        for (StudentIdentifier student : quizMean.keySet()) {
            double grade = (quizMean.get(student) + workRateMean.get(student) + contributionMean.get(student)) * 100 / 3.;
            result.put(student, grade);
        }
        return result;
    }

    private Map<StudentIdentifier, Double> calculateAssessment(String projectName, cheatCheckerMethods method) {
        ArrayList<Performance> totalPerformance = new ArrayList<>();
        //get all students in projectID from DB
        List<String> students = new AssessmentDBCommunication().getStudents(projectName);
        //for each student
        for (String student : students) {
            Integer groupId;
            Performance performance = new Performance();
            StudentIdentifier userNameentifier = new StudentIdentifier(projectName, student);
            groupId = new AssessmentDBCommunication().getGroupByStudent(userNameentifier);
            //todo: answered quizzes vervöllstandigen
            Integer numberOfQuizzes = new AssessmentDBCommunication().getQuizCount(projectName);
            List<Integer> answeredQuizzes = new AssessmentDBCommunication().getAnsweredQuizzes(userNameentifier);
            for (Integer i=answeredQuizzes.size(); i<numberOfQuizzes;i++){
                answeredQuizzes.add(0);
            }
            ArrayList<Map<String, Double>> workRating = new AssessmentDBCommunication().getWorkRating(userNameentifier);
            ArrayList<Map<String, Double>> contributionRating =
                    new AssessmentDBCommunication().getContributionRating(groupId);
            performance.setStudentIdentifier(userNameentifier);
            performance.setQuizAnswer(answeredQuizzes);
            performance.setWorkRating(cheatChecker(workRating, method));
            performance.setContributionRating(cheatChecker(contributionRating, method));
            totalPerformance.add(performance);
        }
        return calculateAssessment(totalPerformance);
    }

    private Map<StudentIdentifier, Double> quizGrade(ArrayList<Performance> totalPerformance) {
        double[] allAssessments = new double[totalPerformance.size()];
        Map<StudentIdentifier, Double> grading = new HashMap<>();

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (Integer quiz : totalPerformance.get(i).getQuizAnswer()) {
                allAssessments[i] += quiz;
            }
            allAssessments[i] = allAssessments[i] / totalPerformance.get(i).getQuizAnswer().size();
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            grading.put(totalPerformance.get(i).getStudentIdentifier(), allAssessments[i]);
        }
        return grading;
    }

    private Map<StudentIdentifier, Double> mapToGrade(Map<StudentIdentifier, Map<String, Double>> ratings) {
        Double allAssessments;
        Map<StudentIdentifier, Double> grading = new HashMap<>();
        for (StudentIdentifier student : ratings.keySet()) {
            if (ratings.get(student) != null) {
                allAssessments = sumOfDimensions(ratings.get(student));
                Double countDimensions = (double) ratings.get(student).size();
                grading.put(student, (allAssessments - 1) / (countDimensions * 4));
            } else {
                grading.put(student, 0.);
            }
        }
        return grading;
    }

    private Double sumOfDimensions(Map rating) {
        Double sumOfDimensions = 0.;
        for (Object o : rating.entrySet()) {
            HashMap.Entry pair = (HashMap.Entry) o;
            Double markForDimension = (Double) pair.getValue();
            sumOfDimensions += markForDimension;
        }
        return sumOfDimensions;
    }

    private Map<String, Double> meanOfWorkRatings(ArrayList<Map<String, Double>> workRatings) {
        HashMap<String, Double> mean = new HashMap<>();
        Double size = (double) workRatings.size();
        for (Object o : workRatings.get(0).entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            mean.put((String) pair.getKey(), 0.0);
        }
        for (Map<String, Double> rating : workRatings) {
            for (Object o : rating.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                Double value = (double) pair.getValue();
                mean.put((String) pair.getKey(), value / size + mean.get(pair.getKey()));
            }
        }
        return mean;
    }

    private Map<String, Double> cheatChecker(ArrayList<Map<String, Double>> workRatings, cheatCheckerMethods method) {
        ArrayList<Map<String, Double>> oneExcludedMeans = new ArrayList<>();
        Map<String, Double> result;
        if (workRatings.size() > 1) {
            for (Map rating : workRatings) {
                ArrayList<Map<String, Double>> possiblyCheating = new ArrayList<>(workRatings);
                possiblyCheating.remove(rating);
                oneExcludedMeans.add(meanOfWorkRatings(possiblyCheating));
            }
        } else {
            if (workRatings.size() < 1) {
                return null;
            }
            oneExcludedMeans.add(meanOfWorkRatings(workRatings));
        }
        if (method.equals(cheatCheckerMethods.median)) {
            workRatings.sort(byMean);
            result = workRatings.get(workRatings.size() / 2); //in favor of student
        } else {
            if (method.equals(cheatCheckerMethods.variance)) {
                Map<String, Double> meanWorkRating = new HashMap<>(meanOfWorkRatings(oneExcludedMeans));
                ArrayList<Map<String, Double>> elementwiseDeviation = new ArrayList<>();
                for (Map<String, Double> rating : oneExcludedMeans) {
                    HashMap<String, Double> shuttle = new HashMap<>();
                    for (String key : rating.keySet()) {
                        Double value = (rating.get(key) - meanWorkRating.get(key)) * (rating.get(key) - meanWorkRating.get(key));
                        shuttle.put(key, value);
                    }
                    elementwiseDeviation.add(shuttle);
                }
                Double deviationOld = 0.;
                Integer key = 0;
                for (Integer i = 0; i < elementwiseDeviation.size(); i++) {
                    Double deviationNew = 0.;
                    for (Double devi : elementwiseDeviation.get(i).values()) {
                        deviationNew += devi;
                    }
                    if (deviationNew > deviationOld) {
                        deviationOld = deviationNew;
                        key = i;
                    }
                }
                result = oneExcludedMeans.get(key);  //gets set of rates with highest deviation in data
                //so without the cheater
            } else {            //without cheatChecking
                result = meanOfWorkRatings(workRatings);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier userNameentifier) {
        return null;
    }

    @Override
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }


    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectName) {
        for (PeerRating peer : peerRatings) {
            StudentIdentifier student = new StudentIdentifier(projectName, peer.getToPeer());
            new AssessmentDBCommunication().writeWorkRatingToDB(student, peer.getFromPeer(), peer.getWorkRating());
        }
    }

    @Override
    public Integer whichGroupToRate(StudentIdentifier student) {
        return new AssessmentDBCommunication().getWhichGroupToRate(student);
    }

    @Override
    public void postContributionRating(String groupId,
                                       String fromStudent,
                                       Map<String, Integer> contributionRating) {
        new AssessmentDBCommunication().writeContributionRatingToDB(groupId, fromStudent, contributionRating);
    }

    @Override
    public void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student) {
        for (String question : questions.keySet()) {
            Map<String, Boolean> whatAreAnswers = new AssessmentDBCommunication().getAnswers(student.getProjectName(), question);
            Map<String, Boolean> wasQuestionAnsweredCorrectly = new HashMap<>();
            Boolean correct = true;
            for (String studentAnswer : questions.get(question)) {
                if (!whatAreAnswers.get(studentAnswer)) {
                    correct = false;
                }
            }
            wasQuestionAnsweredCorrectly.put(question, correct);
            new AssessmentDBCommunication().writeAnsweredQuiz(student, wasQuestionAnsweredCorrectly);
        }
    }

    private Comparator<Map<String, Double>> byMean = (o1, o2) -> {
        Double sumOfO1 = 0.;
        Double sumOfO2 = 0.;
        for (String key : o1.keySet()) {
            sumOfO1 += o1.get(key);
            sumOfO2 += o2.get(key);
        }
        if (sumOfO1.equals(sumOfO2)) {
            return 0;
        } else {
            return sumOfO1 > sumOfO2 ? -1 : 1;
        }
    };
}
