package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;

public interface IReflectionService {

    void startOptionalPortfolioTask(Project project, User target, Phase phase);

    void finishOptionalPortfolioTask(Project project, User target, Phase phase);
}
