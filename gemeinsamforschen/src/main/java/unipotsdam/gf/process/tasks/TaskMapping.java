package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

public class TaskMapping {
    private User subject;
    // object is either a group or a user
    private Group objectGroup;
    private User objectUser;
    private TaskName taskName;
    private Project project;

    public TaskMapping(
            User subject, Group objectGroup, User objectUser, TaskName taskName, Project project) {
        this.subject = subject;
        this.objectGroup = objectGroup;
        this.objectUser = objectUser;
        this.taskName = taskName;
        this.project = project;
    }

    public User getSubject() {
        return subject;
    }

    public void setSubject(User subject) {
        this.subject = subject;
    }

    public Group getObjectGroup() {
        return objectGroup;
    }

    public void setObjectGroup(Group objectGroup) {
        this.objectGroup = objectGroup;
    }

    public User getObjectUser() {
        return objectUser;
    }

    public void setObjectUser(User objectUser) {
        this.objectUser = objectUser;
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
