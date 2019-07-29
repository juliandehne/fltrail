package unipotsdam.gf.process;

import unipotsdam.gf.config.ModuleAvailabilityConfig;
import unipotsdam.gf.interfaces.IPortfolioService;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;

@Resource
@ManagedBean
public class PortfolioProcess {

    @Inject
    private IPortfolioService portfolioService;

    public void startEPortfolioIntroduceTasks(Project project, Group group) {
        if (ModuleAvailabilityConfig.E_PORTFOLIO_MODULE_ENABLED) {
            portfolioService.startStudentPortfolioTask(project, group, Phase.DossierFeedback);
            portfolioService.startDocentPortfolioTask(project, Phase.DossierFeedback);
        }
    }
}