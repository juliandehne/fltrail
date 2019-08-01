package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.CheatCheckerMethods;
import unipotsdam.gf.modules.assessment.controller.model.Contribution;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.tasks.TaskMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeerAssessmentImpl implements IPeerAssessment {

    @Inject
    private AssessmentDAO assessmentDAO;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private FileManagementService fileManagementService;

    @Override
    public List<Contribution> getContributionsFromGroup(Project project, Integer groupId) {
        List<Contribution> result = new ArrayList<>();
        for (FileRole role : FileRole.values()) {
            Contribution contribution = assessmentDAO.getContribution(project, groupId, role);
            switch (role) {
                case DOSSIER:
                    //todo in case of interest, include text Contributions
                    //fullContribution.setTextOfContribution(annotationController.getFinishedDossier(project, groupId));
                    break;
                case PORTFOLIO_ENTRY:
                    break;
            }
            if (contribution != null) {
                result.add(contribution);
            }
        }
        return result;
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


    //todo: its not used yet but nice to have for future. final-grades.jsp
    /*
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
        assert unparsedSolution != null;
        for (String key : unparsedSolution.keySet()) {
            result.put(FileRole.valueOf(key), unparsedSolution.get(key));
        }
        return result;
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
                double countDimensions = (double) ratings.get(student).size();
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
    */

    /**
     * Calculates one out of 3 possible summaries of workRatings.
     * @param workRatings A List, where each element stands for one rating of a student. For example student1 got
     *                    four numerical feedbacks to his group work skills, then workRatings is of size 4.
     *                    For each gradeable item of work skills, the inner map carries the name of the skill
     *                    and a Double which shows the grade.
     * @param method "none" calculates just the average of all entries.
     *               "median" takes the median of the workRatings where entries are sorted by average
     *               "variance" excludes the student with the highest standard deviation to the remaining data.
     *               Afterwards it calculates the mean of remaining data.
     * @return For every dimension of workSkills there is one entry in the result map.
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
                //so without the greatest bias
            } else {            //without cheatChecking
                result = meanOfWorkRatings(workRatings);
            }
        }
        return result;
    }

    @Override
    public Integer whichGroupToRate(Project project, Integer groupId) {
        return taskMapper.getWhichGroupToRate(project, groupId);
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
     * @param project of interest
     * @return List of students with all kinds of marks
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

        // if there are final grades in DB, get them here
        HashMap<User, Double> finalRating = assessmentDAO.getFinalRating(project);

        // get quiz answers   todo: in case of reimplementing quizzes: This is of interest.
        /*   Integer numberOfQuizzes = quizDAO.getQuizCount(project.getName());
            List<Integer> answeredQuizzes = quizDAO.getAnsweredQuizzes(project, user);
            for (Integer i = answeredQuizzes.size(); i < numberOfQuizzes; i++) {
                answeredQuizzes.add(0);
            //performance.setQuizAnswer(answeredQuizzes);
            }*/

        // get the suggestedRating
        HashMap<User, Double> suggestedRating = new HashMap<>();
        for (User user : docentProductRatings.keySet()) {
            Double productOverallSuggestion;
            if (peerProductRatings.get(user) != null) {
                productOverallSuggestion = ((peerProductRatings.get(user) + docentProductRatings.get(user)) / 2);
            } else {
                productOverallSuggestion = docentProductRatings.get(user);
            }
            Double overallSuggestion;
            if (groupRating.get(user) != null) {
                overallSuggestion = (productOverallSuggestion + groupRating.get(user)) / 2;
            } else {
                overallSuggestion = productOverallSuggestion;
            }
            //if (Math.round(overallSuggestion) < Math.round(overallSuggestion)){ }
            suggestedRating.put(user, overallSuggestion);
        }

        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        HashMap<User, User> userMap = new HashMap<>();
        usersByProjectName.forEach(user -> userMap.put(user, user));


        for (User user : suggestedRating.keySet()) {
            UserPeerAssessmentData userPeerAssessmentData = new UserPeerAssessmentData();

            // get the problem flags
            ArrayList<Map<String, Double>> workRating = assessmentDAO.getWorkRating(project, user);
            userPeerAssessmentData.setBeyondStdDeviation(setDeviationFlag(workRating));
            userPeerAssessmentData.setCleanedGroupWorkRating(average(cheatChecker(workRating, CheatCheckerMethods.variance)));

            userPeerAssessmentData.setDocentProductRating(docentProductRatings.get(user));
            userPeerAssessmentData.setGroupProductRating(peerProductRatings.get(user));
            userPeerAssessmentData.setGroupWorkRating(groupRating.get(user));
            userPeerAssessmentData.setSuggestedRating(suggestedRating.get(user));
            userPeerAssessmentData.setFinalRating(finalRating.get(user));
            userPeerAssessmentData.setGroupId(groupDAO.getMyGroup(user, project).getId());
            userPeerAssessmentData.setFiles(fileManagementService.getListOfFiles(user, project.getName()));
            // set flags, too
            userPeerAssessmentData.setUser(userMap.get(user));
            result.add(userPeerAssessmentData);
        }
        return result;
    }

    private Integer setDeviationFlag(ArrayList<Map<String, Double>> workRating) {
        if (workRating == null || workRating.size() == 0) {
            return null;
        }
        Map<String, Double> cleanedMarks = cheatChecker(workRating, CheatCheckerMethods.variance);
        Map<String, Double> averagedMarks = cheatChecker(workRating, CheatCheckerMethods.none);
        Double cleanedAvrg = 0.;
        if (cleanedMarks != null)
            cleanedAvrg = average(cleanedMarks);
        Double averagedAvrg = 0.;
        if (averagedMarks != null)
            averagedAvrg = average(averagedMarks);
        int result = 0;
        if (cleanedAvrg + 0.3 < averagedAvrg) {
            result = -1;
        }
        if (cleanedAvrg - 0.3 > averagedAvrg) {
            result = 1;
        }

        return result;
    }

    private Double average(Map<String, Double> map) {
        if (map != null && map.size() > 0) {
            Double result = 0.;
            for (String itemName : map.keySet()) {
                result += map.get(itemName);
            }
            result = result / map.size();
            return result;
        } else {
            return null;
        }
    }
}
