package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {

    /**
     * student and performance are written to DB
     *
     * @param assessment
     */
    void addAssessmentDataToDB(Assessment assessment);

    Quiz getQuiz(String projectId, String groupId, String author);
    /**
     * will return a saved assessment from the DB
     *
     * @param student
     * @return Assessement = studentIdentifier , performance
     */
    Assessment getAssessmentDataFromDB(StudentIdentifier student);

    /**
     * writes a quiz-question into the DB so other students can benefit from another's insights.
     *
     * @param studentAndQuiz
     */
    void createQuiz(StudentAndQuiz studentAndQuiz) ;

    /**
     * calculate grades for everyone in a list.
     * either it will be overwritten by choice of co- or peer-assessment or it gets a parameter which specifies it.
     *
     *
     * @param totalPerformance @return
     */
    Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance); // calculates marks for every performance and writes it to an array


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
     * @param groupId
     */
    void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId);

    /**
     *
     * @param studentAndQuiz
     * @param quizAnswer
     */
    void answerQuiz(StudentAndQuiz studentAndQuiz, QuizAnswer quizAnswer);
    void deleteQuiz(String quizId);

    Map<String, Double> calculateAssessment(String projectId, String method);
}
