package unipotsdam.gf.modules.quiz;

public class StudentIdentifier {
    public StudentIdentifier(String projectName, String userEmail) {
        this.projectName = projectName;
        this.userEmail = userEmail;
    }

    private String projectName;
    private String userEmail;



    public StudentIdentifier() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }



    @Override
    public String toString() {
        return super.toString();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
