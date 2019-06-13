package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IReflexionService;
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
    private IReflexionService reflexionService;

    public void startOptionalEPortfolioEntryTask(Project project, Group group) {
        reflexionService.startOptionalPortfolioTask(project, group, Phase.DossierFeedback);
    }
}
