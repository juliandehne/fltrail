package unipotsdam.gf.interfaces;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {
    void finalizeAssessment(String projectId);

    Quiz getQuiz(String projectId, String quizId, String author);
    ArrayList<Quiz> getQuiz(String projectId, String author);
    /**
     * will return a saved assessment from the DB
     *
     *
     * @param projectId @return Assessement = studentIdentifier , performance
     */
    Map<StudentIdentifier, Double> getAssessmentForProject(String projectId);

    Double getAssessmentForStudent(StudentIdentifier student);

    //todo: obsolete, get rid of the following function
    Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance);

        /**
         * writes a quiz-question into the DB so other students can benefit from another's insights.
         *
         * @param studentAndQuiz
         */
    void createQuiz(StudentAndQuiz studentAndQuiz) ;

    /**
     *
     * @param studentIdentifier
     * @return
     */
    ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier);

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
     * @param projectId
     * @return all quizzes in projectId
     */
    ArrayList<Quiz> getQuiz(String projectId);

    /**
     * writes the peerRatings into db
     *
     * @param peerRatings
     * @param projectId
     */
    void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId);

    /**
     *
     * @param student
     * @return
     */
    Integer whichGroupToRate(StudentIdentifier student);

    void postContributionRating(String groupId,
                                String fromPeer,
                                Map<String, Integer> contributionRating);

    /**
     *
     * @param questions
     * @param student
     */
    void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student);
    void deleteQuiz(String quizId);

    String whatToRate(StudentIdentifier student);

    Map<StudentIdentifier, ConstraintsMessages> allAssessmentsDone(String projectId);

    void assignMissingAssessmentTasks(Project project);
}
