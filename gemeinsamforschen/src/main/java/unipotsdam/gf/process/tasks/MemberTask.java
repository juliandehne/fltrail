package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

public class MemberTask extends Task {
    /**
     * membertask contains a constructor without user, because the users are queried based on project
     * @param taskName
     * @param project
     * @param progress
     */
    public MemberTask(TaskName taskName, Project project, Progress progress) {
        super(taskName, null, project, progress );
    }

    public MemberTask(
            TaskName taskName, User user, Project project, Progress progress) {
        super(taskName, user, project, progress);
    }
}
