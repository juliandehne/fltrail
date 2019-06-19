package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IPortfolioService;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;

@Resource
@ManagedBean
public class ReflexionProcess {

    @Inject
    private IPortfolioService reflexionService;

    public void startEPortfolioIntroduceTasks(Project project, Group group) {
        reflexionService.startDocentPortfolioTask(project, Phase.DossierFeedback);
        reflexionService.startStudentPortfolioTask(project, group, Phase.DossierFeedback);
    }
}
