package unipotsdam.gf.core.management;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContextDataOutput {
    private Project project;
    private User user;

    public ContextDataOutput() {
    }

    public ContextDataOutput(Project project, User user) {
        this.project = project;
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
