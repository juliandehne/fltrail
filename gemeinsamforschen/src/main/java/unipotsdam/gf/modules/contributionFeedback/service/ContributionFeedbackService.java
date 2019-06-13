package unipotsdam.gf.modules.contributionFeedback.service;

import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.tasks.*;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@ManagedBean
@Resource
public class ContributionFeedbackService implements IContributionFeedback {

    @Inject
    private ContributionFeedbackDAO contributionFeedbackDAO;

    @Inject
    private TaskDAO taskDAO;

    @Override
    public ContributionFeedback saveContributionFeedback(ContributionFeedback contributionFeedback) {
        String uuid = contributionFeedbackDAO.persist(contributionFeedback);
        contributionFeedback.setId(uuid);
        return contributionFeedback;
    }

    @Override
    public ContributionFeedback getContributionFeedback(String id) {
        return contributionFeedbackDAO.findOneById(id);
    }

    @Override
    public ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory, int groupId) {
        return contributionFeedbackDAO.findOneByFullSubmissionIdAndFullSubmissionPartCategoryAndGroupId(fullSubmissionId, fullSubmissionCategory, groupId);
    }

    @Override
    public ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory) {
        return contributionFeedbackDAO.getFeedbackFor(fullSubmissionId, fullSubmissionCategory);
    }

    @Override
    public void updateContributionFeedback(ContributionFeedback contributionFeedback) {
        contributionFeedbackDAO.update(contributionFeedback);
    }

    @Override
    public void endFeedback(String projectName, int groupId) {
        GroupTask task = new GroupTask(TaskName.GIVE_FEEDBACK,groupId, Progress.FINISHED, new Project(projectName) );
        taskDAO.updateGroupTask(task);
    }
}
