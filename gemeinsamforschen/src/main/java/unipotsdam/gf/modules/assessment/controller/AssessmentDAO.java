package unipotsdam.gf.modules.assessment.controller;


import unipotsdam.gf.interfaces.IPeerAssessment;

/**
 * Created by dehne on 18.05.2018.
 */
public abstract class AssessmentDAO implements IPeerAssessment {
    @Override
    public void addAssessmentData(Object data) {
        // here please write DB access
    }

    @Override
    public Object getAssessmentResults(String projectId, String assessmentIdentifier) {
        return null;
    }
}
