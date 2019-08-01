package unipotsdam.gf.modules.fileManagement;

import unipotsdam.gf.modules.group.Group;

public class ContributionStorage {
    private String projectName;
    private String fileLocation;
    private String fileName;
    private FileRole fileRole;
    private String userEmail;
    private Group group;

    ContributionStorage(String projectName, String fileLocation, String fileName, FileRole fileRole, String userEmail, Group group) {
        this.projectName = projectName;
        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.fileRole = fileRole;
        this.userEmail = userEmail;
        this.group = group;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileRole getFileRole() {
        return fileRole;
    }

    public void setFileRole(FileRole fileRole) {
        this.fileRole = fileRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "ContributionStorage{" +
                "projectName='" + projectName + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileRole=" + fileRole +
                ", userEmail='" + userEmail + '\'' +
                ", group=" + group +
                '}';
    }
}
