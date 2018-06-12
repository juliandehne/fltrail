package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

public class PeerAssessmentDummy implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        String[] correctAnswers = new String[2];
        correctAnswers[0] = "42";
        correctAnswers[1] = ""+projectId+" " + groupId;
        String[] wrongAnswers = {"a god created creature", "a sum of my mistakes"};
        if (false){ //projectId with groupId does not exist
            return null;
        }
        Quiz sampleQuiz = new Quiz("multiple","Who am I and if so, how many?", correctAnswers,wrongAnswers);
        return sampleQuiz;
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        return null;
    }

    @Override
    public void createQuiz(StudentIdentifier student, Quiz quiz) {

    }

    @Override
    public int[] calculateAssessment(Performance[] performanceOfAllStudents) {
        return new int[0];
    }

    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }
}
