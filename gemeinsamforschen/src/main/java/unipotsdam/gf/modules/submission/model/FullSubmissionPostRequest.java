package unipotsdam.gf.modules.submission.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullSubmissionPostRequest {

    // variables
    private Integer groupId;
    private boolean personal;
    // TODO should this be named title?
    private String header;
    private String text;
    private String html;
    private String projectName;
    private FileRole fileRole;
    private String id;
    private String reflectionQuestionId;
    private String learningGoalId;

    // have to be set in backend
    private String userEMail;
    private Visibility visibility;

    public FullSubmissionPostRequest() {
    }

    public FullSubmissionPostRequest(Group group, String html, FileRole fileRole, Project project, Visibility
            visibility, String header) {
        this.groupId = group.getId();
        this.html = html;
        this.fileRole = fileRole;
        this.personal = false;
        this.projectName = project.getName();
        this.visibility = visibility;
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    // methods
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public FileRole getFileRole() {
        return fileRole;
    }

    public void setFileRole(FileRole fileRole) {
        this.fileRole = fileRole;
    }

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    public String getUserEMail() {
        return userEMail;
    }

    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReflectionQuestionId() {
        return reflectionQuestionId;
    }

    public void setReflectionQuestionId(String reflectionQuestionId) {
        this.reflectionQuestionId = reflectionQuestionId;
    }

    public String getLearningGoalId() {
        return learningGoalId;
    }

    public void setLearningGoalId(String learningGoalId) {
        this.learningGoalId = learningGoalId;
    }

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "groupId=" + groupId +
                ", personal=" + personal +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                ", html='" + html + '\'' +
                ", projectName='" + projectName + '\'' +
                ", fileRole=" + fileRole +
                ", id='" + id + '\'' +
                ", reflectionQuestionId='" + reflectionQuestionId + '\'' +
                ", userEMail='" + userEMail + '\'' +
                ", visibility=" + visibility +
                ", learningGoalId='" + learningGoalId + '\'' +
                '}';
    }
}
