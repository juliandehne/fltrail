package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

public class PeerAssessment implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        return null;
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
