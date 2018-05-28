package unipotsdam.gf.interfaces;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {
    void addAssessmentData(Object data);
    void calculateAssessment(String projectId);
    Object getAssessmentResults(String projectId, String assessmentIdentifier);
}
