package unipotsdam.gf.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;

import javax.xml.bind.JAXBException;

public interface IPhases {
    /**
     * switch from one phase to the next
     * @param phase the phase to end
     * @param project the project to end the phase in
     */
    void endPhase(Phase phase, Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException;


    void saveState(Project project, Phase phase);
}
