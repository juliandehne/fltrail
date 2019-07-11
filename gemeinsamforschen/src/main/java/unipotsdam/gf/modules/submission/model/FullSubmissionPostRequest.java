package unipotsdam.gf.modules.submission.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import unipotsdam.gf.modules.fileManagement.FileRole;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullSubmissionPostRequest {

    // variables
    private Integer groupId;
    private boolean personal;
    private String header;
    private String text;
    private String html;
    private String projectName;
    private FileRole fileRole;
    private String id;
    private String reflectionQuestionId;

    // have to be set in backend
    private String userEMail;
    private Visibility visibility;

    public FullSubmissionPostRequest() {
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

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "groupId=" + groupId +
                ", personal=" + personal +
                ", text='" + text + '\'' +
                ", html='" + html + '\'' +
                ", projectName='" + projectName + '\'' +
                ", fileRole=" + fileRole +
                ", id='" + id + '\'' +
                ", reflectionQuestionId='" + reflectionQuestionId + '\'' +
                ", userEMail='" + userEMail + '\'' +
                ", visibility=" + visibility +
                '}';
    }
}
