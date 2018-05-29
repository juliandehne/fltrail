package unipotsdam.gf.modules.assessment.controller;


import unipotsdam.gf.interfaces.IPeerAssessment;

/**
 * Created by dehne on 18.05.2018.
 */
public abstract class AssessmentDAO implements IPeerAssessment {

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        return null;
    }
}
