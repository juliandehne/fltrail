package unipotsdam.gf.modules.assessment.controller;


import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

/**
 * Created by dehne on 18.05.2018.
 */
public abstract class AssessmentDAO implements IPeerAssessment {

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        return null;
    }
}
