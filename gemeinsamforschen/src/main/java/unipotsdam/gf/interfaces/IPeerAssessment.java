package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.controller.model.FullContribution;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {
    void finalizeAssessment(Project project);

    List<FullContribution> getContributionsFromGroup(Project project, Integer groupId);

    /**
     * calculates the mean value of all assessments in a project.
     *
     * @param ProjectId
     * @return
     */
    int meanOfAssessment(String ProjectId);

    Integer whichGroupToRate(Project project, User user);

    void postContributionRating(Project project,
                                String groupId,
                                String fromPeer,
                                Map<FileRole, Integer> contributionRating, Boolean isStudent);

}
