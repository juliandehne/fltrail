package unipotsdam.gf.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskName;

import javax.xml.bind.JAXBException;

public interface IPhases {
    /**
     * switch from one phase to the next
     * @param phase the phase to end
     * @param project the project to end the phase in
     */
    void endPhase(Phase phase, Project project, User author) throws Exception;

    void saveState(Project project, Phase phase);

    java.util.List<TaskName> getTaskNames(Phase phase);

    Phase getCorrespondingPhase(TaskName taskName);

    TaskName getLastTask(Phase phase);

    java.util.List<Phase> getPreviousPhases(Phase phase);
}
