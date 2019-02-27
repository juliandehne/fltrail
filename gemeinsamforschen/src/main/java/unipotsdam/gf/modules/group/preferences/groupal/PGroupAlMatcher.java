package unipotsdam.gf.modules.group.preferences.groupal;

import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.preferences.groupal.request.Participants;
import unipotsdam.gf.modules.group.preferences.groupal.request.ParticipantsHolder;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PGroupAlMatcher extends GroupAlMatcher {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    UserDAO userDAO;

    List<User> calculatedList;
    List<User> restUsers;

    @Override
    public List<Group> calculateGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {

        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());

        int userCount = usersByProjectName.size();
        int calculateCount = userCount - (userCount % 3);

        calculatedList = usersByProjectName.subList(0, calculateCount);
        restUsers = usersByProjectName.subList(calculateCount, userCount);

        Iterator<User> iterator = restUsers.iterator();
        // calculate groups with super class
        List<Group> groups = createGroups(project, 3);

        for (Group group : groups) {
            if (iterator.hasNext()) {
                group.getMembers().add(iterator.next());
            }
        }

        return groups;
    }

    @Override
    public List<Group> createGroups(Project project, int groupsize)
            throws JsonProcessingException, JAXBException, WrongNumberOfParticipantsException {
        return super.createGroups(project, groupsize);
    }

    @Override
    protected ParticipantsHolder adjustUserCount(
            ParticipantsHolder responses) {
        List<Integer> restUserIds = restUsers.stream().map(User::getId).collect(Collectors.toList());
        List<Participants> participants = responses.getParticipants();
        List<Participants> adjustedParticpants = new ArrayList<>();
        for (Participants participant : participants) {
            if (!restUserIds.contains(participant.getId())) {
                adjustedParticpants.add(participant);
            }
        }
        responses.setParticipants(adjustedParticpants);
        return super.adjustUserCount(responses);
    }
}