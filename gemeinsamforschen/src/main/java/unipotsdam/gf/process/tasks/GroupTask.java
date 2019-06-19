package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.project.Project;

/**
 * makes sure that groupid is set in task when talking about a group task
 */
public class GroupTask extends MemberTask {

    public GroupTask(
            TaskName taskName, Integer groupId, Progress progress, Project project) {
        super(taskName, project, progress);
        setGroupTask(groupId);
    }
}
