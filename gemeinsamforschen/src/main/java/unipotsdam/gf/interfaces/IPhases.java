package unipotsdam.gf.interfaces;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.states.model.ProjectPhase;

public interface IPhases {
    /**
     * switch from one phase to the next
     * @param projectPhase
     * @param project
     */
    public void endPhase(ProjectPhase projectPhase, Project project);

    /**
     * the dependency to feedback should be settable externally for test reasons
     * @param feedback
     */
    void setFeedback(Feedback feedback);
}
