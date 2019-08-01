package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.UserPeerAssessmentData;
import unipotsdam.gf.modules.assessment.controller.model.Contribution;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.project.Project;

import java.util.List;
import java.util.Map;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {

    List<Contribution> getContributionsFromGroup(Project project, Integer groupId);

    Integer whichGroupToRate(Project project, Integer groupId);

    void postContributionRating(Project project,
                                String groupId,
                                String fromPeer,
                                Map<FileRole, Integer> contributionRating, Boolean isStudent);

    List<UserPeerAssessmentData> getUserAssessmentsFromDB(Project project);
}
