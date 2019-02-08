package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.xml.bind.JAXBException;
import java.util.List;

public class SingleGroupMatcher implements GroupFormationAlgorithm {
    @Override
    public List<Group> calculateGroups(Project project) throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        return null;
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) throws Exception {
        // do nothing
    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {
        // do nothing
    }

    @Override
    public int getMinNumberOfStudentsNeeded() {
        return 2;
    }
}
