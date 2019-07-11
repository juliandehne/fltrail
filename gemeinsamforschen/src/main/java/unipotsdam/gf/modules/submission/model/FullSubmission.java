package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.fileManagement.FileRole;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmission {

    // variables
    private String header;
    private String id;
    private long timestamp;
    private Integer groupId;
    private String userEmail;
    private String text;
    private FileRole fileRole;
    private String projectName;
    private Visibility visibility;

    public FullSubmission(String id, long timestamp, Integer groupId, String text, FileRole fileRole, String projectName, Visibility visibility) {
        this(id, timestamp, groupId, null, text, fileRole, projectName, visibility);
    }

    public FullSubmission(String id, long timestamp, Integer groupId, String userEmail, String text, FileRole fileRole, String projectName, Visibility visibility) {
        this.id = id;
        this.timestamp = timestamp;
        this.groupId = groupId;
        this.userEmail = userEmail;
        this.text = text;
        this.fileRole = fileRole;
        this.projectName = projectName;
        this.visibility = visibility;
    }

    public FullSubmission(String id, long timestamp, Integer groupId, String userEmail, String header, String text, FileRole fileRole, String projectName, Visibility visibility) {
        this.id = id;
        this.timestamp = timestamp;
        this.groupId = groupId;
        this.userEmail = userEmail;
        this.header = header;
        this.text = text;
        this.fileRole = fileRole;
        this.projectName = projectName;
        this.visibility = visibility;
    }

    ;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public FullSubmission(String submissionId) {
        this.id = submissionId;
    }

    // methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileRole getFileRole() {
        return fileRole;
    }

    public void setFileRole(FileRole fileRole) {
        this.fileRole = fileRole;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "FullSubmission{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", groupId=" + groupId +
                ", userEmail='" + userEmail + '\'' +
                ", text='" + text + '\'' +
                ", fileRole=" + fileRole +
                ", projectName='" + projectName + '\'' +
                ", visibility=" + visibility +
                '}';
    }

}
