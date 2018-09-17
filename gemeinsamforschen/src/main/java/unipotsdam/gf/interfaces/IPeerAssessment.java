package unipotsdam.gf.interfaces;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
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

    /**
     * student and performance are written to DB
     *
     * @param assessment
     */
    void addAssessmentDataToDB(Assessment assessment);

    Quiz getQuiz(String projectId, String quizId, String author);
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

    Map<StudentIdentifier, Double> calculateAssessment(String projectId, String method);


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
     * @param questions
     * @param student
     */
    void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student);

    void deleteQuiz(String quizId);

    String whatToRate(StudentIdentifier student);

    Boolean allAssessmentsDone(String projectId);

    void assignMissingAssessmentTasks(Project project);
}
