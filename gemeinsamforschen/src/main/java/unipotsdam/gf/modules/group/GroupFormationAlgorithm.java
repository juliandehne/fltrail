package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;

import javax.xml.bind.JAXBException;

public interface GroupFormationAlgorithm {
    java.util.List<Group> calculateGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException;
    int getMinNumberOfStudentsNeeded();
}
