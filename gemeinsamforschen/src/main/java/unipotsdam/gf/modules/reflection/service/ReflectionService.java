package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.interfaces.IReflectionService;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Importance;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;

@ManagedBean
@Resource
public class ReflectionService implements IReflectionService {

    @Inject
    private TaskDAO taskDAO;

    public void startOptionalPortfolioTask(Project project, User target, Phase phase) {
        persistOptionalEntryTask(project, target, Progress.JUSTSTARTED, phase);
    }

    public void finishOptionalPortfolioTask(Project project, User target, Phase phase) {
        persistOptionalEntryTask(project, target, Progress.FINISHED, phase);
    }

    private void persistOptionalEntryTask(Project project, User target, Progress progress, Phase phase) {
        Task task = taskDAO.createUserDefaultWithoutDeadline(project, target, TaskName.OPTIONAL_PORTFOLIO_ENTRY, phase, Importance.LOW, progress);
        taskDAO.persist(task);
    }
}
