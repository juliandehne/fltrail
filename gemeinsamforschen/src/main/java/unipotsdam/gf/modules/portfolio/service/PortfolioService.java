package unipotsdam.gf.modules.portfolio.service;

import unipotsdam.gf.interfaces.IPortfolioService;
import unipotsdam.gf.modules.group.Group;
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
public class PortfolioService implements IPortfolioService {

    @Inject
    private TaskDAO taskDAO;

    public void startStudentPortfolioTask(Project project, Group group, Phase phase) {
        group.getMembers().forEach(target -> {
            persistTask(project, target, Progress.JUSTSTARTED, TaskName.INTRODUCE_E_PORTFOLIO_STUDENT, phase);
        });

    }

    public void finishStudentPortfolioTask(Project project, Group group, Phase phase) {
        group.getMembers().forEach(target -> {
            persistTask(project, target, Progress.FINISHED, TaskName.INTRODUCE_E_PORTFOLIO_STUDENT, phase);
        });
    }

    @Override
    public void startDocentPortfolioTask(Project project, Phase phase) {
        taskDAO.persistTeacherTask(project, TaskName.INTRODUCE_E_PORTFOLIO_DOCENT, phase);
    }

    @Override
    public void finishDocentPortfolioTask(Project project, Phase phase) {
        persistTask(project, new User(project.getAuthorEmail()), Progress.FINISHED, TaskName.INTRODUCE_E_PORTFOLIO_DOCENT, phase);
    }

    private void persistTask(Project project, User target, Progress progress, TaskName taskName, Phase phase) {
        Task task = taskDAO.createUserDefaultWithoutDeadline(project, target, taskName, phase, Importance.LOW, progress);
        taskDAO.persist(task);
    }
}
