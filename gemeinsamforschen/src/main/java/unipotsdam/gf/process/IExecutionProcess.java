package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Project;

public interface IExecutionProcess {
    void start(Project project);

    boolean isPhaseCompleted(Project project);

    void finishPhase(Project project);
}
