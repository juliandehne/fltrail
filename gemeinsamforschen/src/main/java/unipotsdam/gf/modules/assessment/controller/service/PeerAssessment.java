package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsMessages;
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

    @Inject
    private AssessmentDBCommunication assessmentDBCommunication;

    @Inject
    private GroupDAO groupDAO;

    @Override
    public void finalizeAssessment(Project project){
        cheatCheckerMethods method = assessmentDBCommunication.getAssessmentMethod(project);
        Map<User, Double> grading = calculateAssessment(project, method);
        assessmentDBCommunication.writeGradesToDB(project, grading);
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
        return assessmentDBCommunication.getGradesFromDB(student);
    }

    @Override
    public Map<String, String> getContributionsFromGroup(Project project, Integer groupId){
        //todo: implement
        return null;
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
    public String whatToRate(Project project, User user) {
        Integer groupId = groupDAO.getGroupByStudent(project, user);
        ArrayList<String> groupMembers = assessmentDBCommunication.getStudentsByGroupAndProject(groupId);
        for (String peer : groupMembers) {
            if (!peer.equals(user.getEmail())) {
                StudentIdentifier groupMember = new StudentIdentifier(project.getName(), peer);
                if (!assessmentDBCommunication.getWorkRating(groupMember, user.getEmail())) {
                    return "workRating";
                }
            }
        }
        ArrayList<Integer> answers = assessmentDBCommunication.getAnsweredQuizzes(project, user);
        if (answers == null) {
            return "quiz";
        }
        Integer groupToRate = assessmentDBCommunication.getWhichGroupToRate(project, user);
        if (!assessmentDBCommunication.getContributionRating(groupToRate, user.getEmail())) {
            return "contributionRating";
        }
        return "done";
    }

    @Override
    public Map<StudentIdentifier, ConstraintsMessages> allAssessmentsDone(String projectName) {
        Map<StudentIdentifier, ConstraintsMessages> result;
        result = assessmentDBCommunication.missingAssessments(projectName);
        return result;
    }

    @Override
    public void assignMissingAssessmentTasks(Project project) {

    }
    @Override
    public Map<User, Double> calculateAssessment(ArrayList<Performance> totalPerformance) {
        Map<User, Double> quizMean = new HashMap<>(quizGrade(totalPerformance));
        Map<User, Map<String, Double>> workRating = new HashMap<>();
        Map<User, Map<String, Double>> contributionRating = new HashMap<>();
        for (Performance performance : totalPerformance) {
            workRating.put(performance.getUser(), performance.getWorkRating());
            contributionRating.put(performance.getUser(), performance.getContributionRating());
        }
        Map<User, Double> workRateMean = new HashMap<>(mapToGrade(workRating));
        Map<User, Double> contributionMean = new HashMap<>(mapToGrade(contributionRating));
        Map<User, Double> result = new HashMap<>();
        for (User student : quizMean.keySet()) {
            double grade = (quizMean.get(student) + workRateMean.get(student) + contributionMean.get(student)) * 100 / 3.;
            result.put(student, grade);
        }
        return result;
    }

    private Map<User, Double> calculateAssessment(Project project, cheatCheckerMethods method) {
        ArrayList<Performance> totalPerformance = new ArrayList<>();
        //get all students in projectID from DB
        List<String> students = assessmentDBCommunication.getStudents(project.getName());
        //for each student
        for (String student : students) {
            User user = new User(student);
            Integer groupId;
            Performance performance = new Performance();
            groupId = groupDAO.getGroupByStudent(project, user);
            Integer numberOfQuizzes = assessmentDBCommunication.getQuizCount(project.getName());
            List<Integer> answeredQuizzes = assessmentDBCommunication.getAnsweredQuizzes(project, user);
            for (Integer i=answeredQuizzes.size(); i<numberOfQuizzes;i++){
                answeredQuizzes.add(0);
            }
            ArrayList<Map<String, Double>> workRating = assessmentDBCommunication.getWorkRating(project, user);
            ArrayList<Map<String, Double>> contributionRating =
                    assessmentDBCommunication.getContributionRating(groupId);
            performance.setProject(project);
            performance.setUser(user);
            performance.setQuizAnswer(answeredQuizzes);
            performance.setWorkRating(cheatChecker(workRating, method));
            performance.setContributionRating(cheatChecker(contributionRating, method));
            totalPerformance.add(performance);
        }
        return calculateAssessment(totalPerformance);
    }

    private Map<User, Double> quizGrade(ArrayList<Performance> totalPerformance) {
        double[] allAssessments = new double[totalPerformance.size()];
        Map<User, Double> grading = new HashMap<>();

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (Integer quiz : totalPerformance.get(i).getQuizAnswer()) {
                allAssessments[i] += quiz;
            }
            allAssessments[i] = allAssessments[i] / totalPerformance.get(i).getQuizAnswer().size();
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            grading.put(totalPerformance.get(i).getUser(), allAssessments[i]);
        }
        return grading;
    }

    private Map<User, Double> mapToGrade(Map<User, Map<String, Double>> ratings) {
        Double allAssessments;
        Map<User, Double> grading = new HashMap<>();
        for (User student : ratings.keySet()) {
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
            assessmentDBCommunication.writeWorkRatingToDB(student,peer.getFromPeer(), peer.getWorkRating());
        }
    }

    @Override
    public Integer whichGroupToRate(Project project, User user) {
        return assessmentDBCommunication.getWhichGroupToRate(project, user);
    }

    @Override
    public void postContributionRating(String groupId,
                                       String fromStudent,
                                       Map<String, Integer> contributionRating) {
        assessmentDBCommunication.writeContributionRatingToDB(groupId, fromStudent, contributionRating);
    }

    @Override
    public void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student) {
        for (String question : questions.keySet()) {
            Map<String, Boolean> whatAreAnswers = assessmentDBCommunication.getAnswers(student.getProjectName(), question);
            Map<String, Boolean> wasQuestionAnsweredCorrectly = new HashMap<>();
            Boolean correct = true;
            for (String studentAnswer : questions.get(question)) {
                if (!whatAreAnswers.get(studentAnswer)) {
                    correct = false;
                }
            }
            wasQuestionAnsweredCorrectly.put(question, correct);
            assessmentDBCommunication.writeAnsweredQuiz(student, wasQuestionAnsweredCorrectly);
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
