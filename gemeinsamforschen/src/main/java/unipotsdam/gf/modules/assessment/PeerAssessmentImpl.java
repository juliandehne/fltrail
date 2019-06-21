package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.assessment.controller.model.CheatCheckerMethods;
import unipotsdam.gf.modules.assessment.controller.model.FullContribution;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.tasks.TaskMapper;

import javax.inject.Inject;
import java.util.*;

public class PeerAssessmentImpl implements IPeerAssessment {

    @Inject
    private AssessmentDAO assessmentDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private AnnotationController annotationController;

    @Inject
    private UserDAO userDAO;

    @Override
    public void finalizeAssessment(Project project) {
        //assessmentDAO.writeGradesToDB(project, grading);
        // TODO implement
    }

    @Override
    public List<FullContribution> getContributionsFromGroup(Project project, Integer groupId) {
        List<FullContribution> result = new ArrayList<>();
        for (FileRole role : FileRole.values()) {
            FullContribution fullContribution = assessmentDAO.getContribution(project, groupId, role);
            switch (role) {
                case DOSSIER:
                    /**
                     * @AXEL ev. text der contribution später wieder hinzufügen
                     */
                    //fullContribution.setTextOfContribution(annotationController.getFinishedDossier(project, groupId));
                    break;
                case PORTFOLIO:
                    break;
            }
            if (fullContribution != null) {
                result.add(fullContribution);
            }
        }
        return result;
    }

    private Map<User, Double> calculateAssessmentSuggestion(ArrayList<Performance> totalPerformance) {
        //Map<User, Double> quizMean = new HashMap<>(quizGrade(totalPerformance));
        Map<User, Map<String, Double>> workRating = new HashMap<>();
        Map<User, Map<FileRole, Double>> contributionRating = new HashMap<>();
        for (Performance performance : totalPerformance) {
            workRating.put(performance.getUser(), performance.getWorkRating());
            contributionRating.put(performance.getUser(), performance.getContributionRating());
        }
        Map<User, Double> workRateMean = new HashMap<>(mapToGrade(workRating));
        Map<User, Double> contributionMean = new HashMap<>(mapToGradeContribution(contributionRating));
        Map<User, Double> result = new HashMap<>();
        for (User student : workRating.keySet()) {
            double grade =
                    (workRateMean.get(student) + contributionMean.get(student)) * 100 / 3.;
            result.put(student, grade);
        }
        return result;
    }

    private Map<User, Double> calculateSuggestions(Project project, CheatCheckerMethods method) {
        ArrayList<Performance> totalPerformance = new ArrayList<>();
        //get all students in projectID from DB
        List<String> students = assessmentDAO.getStudents(project.getName());
        //for each student
        for (String student : students) {
            User user = new User(student);
            Integer groupId;
            Performance performance = new Performance();
            groupId = groupDAO.getGroupByStudent(project, user);
            /*   Integer numberOfQuizzes = quizDAO.getQuizCount(project.getName());
            List<Integer> answeredQuizzes = quizDAO.getAnsweredQuizzes(project, user);
            for (Integer i = answeredQuizzes.size(); i < numberOfQuizzes; i++) {
                answeredQuizzes.add(0);
            }*/
            ArrayList<Map<String, Double>> workRating = assessmentDAO.getWorkRating(project, user);
            ArrayList<Map<FileRole, Double>> contributionRating =
                    assessmentDAO.getContributionRating(groupId, true);
            performance.setProject(project);
            performance.setUser(user);
            //performance.setQuizAnswer(answeredQuizzes);
            performance.setWorkRating(cheatChecker(workRating, method));
            performance.setContributionRating(cheatCheckerContributions(contributionRating, method));
            totalPerformance.add(performance);
        }
        return calculateAssessmentSuggestion(totalPerformance);
    }

    private Map<User, Double> mapToGradeContribution(Map<User, Map<FileRole, Double>> ratings) {
        //convert Map<User, Map<ContributionCategory, Double>> to Map<User, Map<String, Double>>
        Map<User, Map<String, Double>> convertTo = new HashMap<>();
        for (User user : ratings.keySet()) {
            Map<String, Double> marksForContribution = new HashMap<>();
            for (FileRole fileRole : ratings.get(user).keySet()) {
                marksForContribution.put(fileRole.toString(), ratings.get(user).get(fileRole));
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

    private Map<FileRole, Double> cheatCheckerContributions(
            ArrayList<Map<FileRole, Double>> contributionRatings, CheatCheckerMethods method) {
        //convert ArrayList<Map<ContributionCategory, Double>> to ArrayList<Map<String, Double>>
        ArrayList<Map<String, Double>> ratings = new ArrayList<>();
        for (Map<FileRole, Double> rating : contributionRatings) {
            Map<String, Double> markForContribution = new HashMap<>();
            for (FileRole fileRole : rating.keySet()) {
                markForContribution.put(fileRole.toString(), rating.get(fileRole));
            }
            ratings.add(markForContribution);
        }

        Map<String, Double> unparsedSolution = cheatChecker(ratings, method);
        Map<FileRole, Double> result = new HashMap<>();

        //convert ArrayList<Map<String, Double>> back to ArrayList<Map<FileRole, Double>>
        for (String key : unparsedSolution.keySet()) {
            result.put(FileRole.valueOf(key), unparsedSolution.get(key));
        }
        return result;
    }

    /**
     * schön
     * @param workRatings
     * @param method
     * @return
     */
    private Map<String, Double> cheatChecker(ArrayList<Map<String, Double>> workRatings, CheatCheckerMethods method) {
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
        if (method.equals(CheatCheckerMethods.median)) {
            workRatings.sort(byMean);
            result = workRatings.get(workRatings.size() / 2); //in favor of student
        } else {
            if (method.equals(CheatCheckerMethods.variance)) {
                Map<String, Double> meanWorkRating = new HashMap<>(meanOfWorkRatings(oneExcludedMeans));
                ArrayList<Map<String, Double>> elementwiseDeviation = new ArrayList<>();
                for (Map<String, Double> rating : oneExcludedMeans) {
                    HashMap<String, Double> shuttle = new HashMap<>();
                    for (String key : rating.keySet()) {
                        Double value = (rating.get(key) - meanWorkRating.get(key)) * (rating.get(key) - meanWorkRating
                                .get(key));
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
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }

    @Override
    public Integer whichGroupToRate(Project project, User user) {
        return taskMapper.getWhichGroupToRate(project, user);
    }

    @Override
    public void postContributionRating(
            Project project, String groupId, String fromStudent, Map<FileRole, Integer> contributionRating, Boolean
            isStudent) {
        assessmentDAO.writeContributionRatingToDB(project, groupId, fromStudent, contributionRating, isStudent);
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

    /**
     * GET THE DATA and suggestions, including the PROBLEM CASES
     * @param project
     * @return
     */
    @Override
    public List<UserPeerAssessmentData> getUserAssessmentsFromDB(Project project) {
        List<UserPeerAssessmentData> result = new ArrayList<>();

        // get the internal rating aggregated

        // get the  peer product rating
        HashMap<User, Double> peerProductRatings = assessmentDAO.getPeerProductRatings(project);

        // get the docent product rating
        HashMap<User, Double> docentProductRatings = assessmentDAO.getDocentProductRatings(project);

        // get the internal ratings
        HashMap<User, Double> groupRating = assessmentDAO.getGroupRating(project);

        HashMap<User, Double> finalRating = assessmentDAO.getFinalRating(project);

        // get the suggestedRating
        HashMap<User, Double> suggestedRating = new HashMap<>();
        for (User user : peerProductRatings.keySet()) {
            Double productOverallSuggestion = ((peerProductRatings.get(user) + docentProductRatings.get(user)) / 2);
            Double overallSuggestion = (productOverallSuggestion + groupRating.get(user)) / 2;
            suggestedRating.put(user, overallSuggestion);
        }

        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        HashMap<User, User> userMap = new HashMap<>();
        usersByProjectName.forEach(user -> userMap.put(user, user));


        for (User user : suggestedRating.keySet()) {
            UserPeerAssessmentData userPeerAssessmentData = new UserPeerAssessmentData();

            // get the problem flags
            /* todo @Axel Ich muss da später drüber nachdenken. Jetzt mach ich erstmal kleine issues
            ArrayList<Map<String, Double>> workRating = assessmentDAO.getWorkRating(project, user);
            cheatChecker(workRating, CheatCheckerMethods.variance);
*/
            userPeerAssessmentData.setDocentProductRating(docentProductRatings.get(user));
            userPeerAssessmentData.setGroupProductRating(peerProductRatings.get(user));
            userPeerAssessmentData.setGroupWorkRating(groupRating.get(user));
            userPeerAssessmentData.setSuggestedRating(suggestedRating.get(user));
            userPeerAssessmentData.setFinalRating(finalRating.get(user));
            // set flags, too
            userPeerAssessmentData.setUser(userMap.get(user));
            result.add(userPeerAssessmentData);
        }
        return result;
    }
}
