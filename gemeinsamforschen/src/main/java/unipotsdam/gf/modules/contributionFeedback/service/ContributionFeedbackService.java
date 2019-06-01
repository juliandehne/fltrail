package unipotsdam.gf.modules.contributionFeedback.service;

import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;

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
    public void updateContributionFeedback(ContributionFeedback contributionFeedback) {
        contributionFeedbackDAO.update(contributionFeedback);
    }

    @Override
    public void endFeedback(Task task) {
        taskDAO.endFeedback(task);
    }
}
