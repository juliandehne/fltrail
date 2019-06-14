package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;

public interface IReflexionService {

    void startOptionalPortfolioTask(Project project, Group group, Phase phase);

    void finishOptionalPortfolioTask(Project project, Group group, Phase phase);
}
