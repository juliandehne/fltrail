package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.PeerRating;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentAndQuiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {
    void finalizeAssessment(Project project);

    Quiz getQuiz(String projectName, String quizId, String author);
    ArrayList<Quiz> getQuiz(String projectName, String author);
    /**
     * will return a saved assessment from the DB
     *
     *
     * @param projectName @return Assessement = userNameentifier , performance
     */
    Map<StudentIdentifier, Double> getAssessmentForProject(String projectName);

    Double getAssessmentForStudent(StudentIdentifier student);

    Map<String, String> getContributionsFromGroup(Project project, Integer groupId);

    Map<User, Double> calculateAssessment(ArrayList<Performance> totalPerformance);

        /**
         * writes a quiz-question into the DB so other students can benefit from another's insights.
         *
         * @param studentAndQuiz
         */
    void createQuiz(StudentAndQuiz studentAndQuiz) ;

    /**

     *
     * @param userNameentifier
     * @return
     */
    ArrayList<Performance> getTotalAssessment(StudentIdentifier userNameentifier);

    /**
     * calculates the mean value of all assessments in a project.
     *
     * @param ProjectId
     * @return
     */
    int meanOfAssessment(String ProjectId);

    /**
     * returns all quizzes in a project
     *
     * @param projectName
     * @return all quizzes in projectName
     */
    ArrayList<Quiz> getQuiz(String projectName);

    /**
     * writes the peerRatings into db
     *
     * @param peerRatings
     * @param projectName
     */
    void postPeerRating(ArrayList<PeerRating> peerRatings, String projectName);

    Integer whichGroupToRate(Project project, User user);

    void postContributionRating(String groupId,
                                String fromPeer,
                                Map<String, Integer> contributionRating);

    /**
     * @param questions
     * @param student
     */
    void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student);

    void deleteQuiz(String quizId);

    String whatToRate(Project project, User user);

    Map<StudentIdentifier, ConstraintsMessages> allAssessmentsDone(String projectName);

    void assignMissingAssessmentTasks(Project project);
}
