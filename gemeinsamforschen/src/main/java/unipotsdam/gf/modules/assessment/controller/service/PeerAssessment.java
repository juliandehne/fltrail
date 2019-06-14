package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsMessages;
import unipotsdam.gf.process.tasks.TaskMapper;

import javax.inject.Inject;
import java.util.*;

public class PeerAssessment implements IPeerAssessment {

    @Inject
    private Management management;

    @Inject
    private AssessmentDBCommunication assessmentDBCommunication;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private AnnotationController annotationController;

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
    public List<FullContribution> getContributionsFromGroup(Project project, Integer groupId){
        List<FullContribution> result = new ArrayList<>();
        for (ContributionCategory role : ContributionCategory.values()) {
            FullContribution fullContribution = new FullContribution();
            Contribution contribution = assessmentDBCommunication.getContribution(project, groupId, role);
            if (contribution != null){
                fullContribution.setNameOfFile(contribution.getNameOfFile());
                fullContribution.setPathToFile(contribution.getPathToFile());
            }else{
                fullContribution.setNameOfFile(null);
                fullContribution.setPathToFile(null);
            }
            fullContribution.setRoleOfContribution(role);
            fullContribution.setTextOfContribution("");
            switch (role) {
                case DOSSIER:
                    fullContribution.setTextOfContribution(annotationController.getFinishedDossier(project,groupId));
                    break;
                case PORTFOLIO:
                    break;
            }
            result.add(fullContribution);
        }
        return result;
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
        Integer groupToRate = taskMapper.getWhichGroupToRate(project, user);
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
        Map<User, Map<ContributionCategory, Double>> contributionRating = new HashMap<>();
        for (Performance performance : totalPerformance) {
            workRating.put(performance.getUser(), performance.getWorkRating());
            contributionRating.put(performance.getUser(), performance.getContributionRating());
        }
        Map<User, Double> workRateMean = new HashMap<>(mapToGrade(workRating));
        Map<User, Double> contributionMean = new HashMap<>(mapToGradeContribution(contributionRating));
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
            ArrayList<Map<ContributionCategory, Double>> contributionRating =
                    assessmentDBCommunication.getContributionRating(groupId);
            performance.setProject(project);
            performance.setUser(user);
            performance.setQuizAnswer(answeredQuizzes);
            performance.setWorkRating(cheatChecker(workRating, method));
            performance.setContributionRating(cheatCheckerContributions(contributionRating, method));
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

    private Map<User, Double> mapToGradeContribution(Map<User, Map<ContributionCategory, Double>> ratings) {
        //convert Map<User, Map<ContributionCategory, Double>> to Map<User, Map<String, Double>>
        Map<User, Map<String, Double>> convertTo = new HashMap<>();
        for (User user : ratings.keySet()){
            Map<String, Double> marksForContribution = new HashMap<>();
            for (ContributionCategory contributionCategory : ratings.get(user).keySet()) {
                marksForContribution.put(contributionCategory.toString(), ratings.get(user).get(contributionCategory));
            }
            convertTo.put(user, marksForContribution);
        }
        return mapToGrade(convertTo);

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

    private Map<ContributionCategory, Double> cheatCheckerContributions(ArrayList<Map<ContributionCategory, Double>> contributionRatings, cheatCheckerMethods method) {
        //convert ArrayList<Map<ContributionCategory, Double>> to ArrayList<Map<String, Double>>
        ArrayList<Map<String, Double>> ratings = new ArrayList<>();
        for (Map<ContributionCategory, Double> rating : contributionRatings) {
            Map<String, Double> markForContribution = new HashMap<>();
            for (ContributionCategory contribution : rating.keySet()) {
                markForContribution.put(contribution.toString(), rating.get(contribution));
            }
            ratings.add(markForContribution);
        }

        Map<String, Double> unparsedSolution = cheatChecker(ratings, method);
        Map<ContributionCategory, Double> result = new HashMap<>();

        //convert ArrayList<Map<String, Double>> back to ArrayList<Map<ContributionCategory, Double>>
        for (String key : unparsedSolution.keySet()){
            result.put(ContributionCategory.valueOf(key), unparsedSolution.get(key));
        }
        return result;
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
        return taskMapper.getWhichGroupToRate(project, user);
    }

    @Override
    public void postContributionRating(Project project,
                                       String groupId,
                                       String fromStudent,
                                       Map<ContributionCategory, Integer> contributionRating) {
        assessmentDBCommunication.writeContributionRatingToDB(project, groupId, fromStudent, contributionRating);
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
