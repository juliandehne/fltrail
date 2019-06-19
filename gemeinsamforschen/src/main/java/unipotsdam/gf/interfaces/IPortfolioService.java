package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;

public interface IPortfolioService {

    void startStudentPortfolioTask(Project project, Group group, Phase phase);

    void finishStudentPortfolioTask(Project project, Group group, Phase phase);

    void startDocentPortfolioTask(Project project, Phase phase);

    void finishDocentPortfolioTask(Project project, Phase phase);
}
