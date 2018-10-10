package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;

public interface IPhases {
    /**
     * switch from one phase to the next
     * @param phase the phase to end
     * @param project the project to end the phase in
     */
    void endPhase(Phase phase, Project project);

    /**
     * the dependency to feedback should be settable externally for test reasons
     * @param feedback the feedback that is send
     */
    void setFeedback(Feedback feedback);
}
