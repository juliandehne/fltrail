package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.CompbaseDownException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.xml.bind.JAXBException;

/** todo route group relevant user data over interface
 *
 */
public interface GroupFormationAlgorithm {
    java.util.List<Group> calculateGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException;
    // in case of compbase all the data is added iteratively over the interface
    void addGroupRelevantData(Project project, User user, Object data) throws Exception;
    // in case of groupal the data is added to the mysql db
    void addGroupRelevantData(Project project, Object data);

    int getMinNumberOfStudentsNeeded();
}
