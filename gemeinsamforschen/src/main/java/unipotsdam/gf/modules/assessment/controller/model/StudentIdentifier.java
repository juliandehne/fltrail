package unipotsdam.gf.modules.assessment.controller.model;

public class StudentIdentifier {
    private String projectId;
    private String studentId;

    public StudentIdentifier(String projectId, String studentId) {
        this.projectId = projectId;
        this.studentId = studentId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "StudentIdentifier{" +
                "projectId='" + projectId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
