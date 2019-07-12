package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;

public class DummyExecutionProcess implements IExecutionProcess {

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    public void start(Project project) {
        taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_REFLECTION, Phase.Execution);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_EXECUTION_PHASE, Phase.Execution);
    }

    @Override
    public boolean isPhaseCompleted(Project project) {
        return true;
    }

    @Override
    public void finishPhase(Project project) throws Exception {
        User user = userDAO.getUserByEmail(project.getAuthorEmail());
        Task task = new Task(TaskName.CLOSE_EXECUTION_PHASE, user, project, Progress.FINISHED );
        taskDAO.updateForUser(task);
    }
}
