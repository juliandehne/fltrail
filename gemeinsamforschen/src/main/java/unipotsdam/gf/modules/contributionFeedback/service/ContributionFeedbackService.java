package unipotsdam.gf.modules.contributionFeedback.service;

import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.tasks.GroupTask;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

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
        return contributionFeedbackDAO.persist(contributionFeedback);
    }

    @Override
    public ContributionFeedback getContributionFeedback(String id) {
        return contributionFeedbackDAO.findOneById(id);
    }

    @Override
    public ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory, int groupId) {
        return contributionFeedbackDAO.findOneBy(fullSubmissionId, fullSubmissionCategory, groupId);
    }

    @Override
    public ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory) {
        return contributionFeedbackDAO.getFeedbackFor(fullSubmissionId, fullSubmissionCategory);
    }

    @Override
    public List<ContributionFeedback> getContributionFeedbacksForFullSubmission(String fullSubmissionId) {
        return contributionFeedbackDAO.findAll(fullSubmissionId);
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
