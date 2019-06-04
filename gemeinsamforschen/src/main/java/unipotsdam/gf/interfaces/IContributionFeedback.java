package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.process.tasks.Task;

public interface IContributionFeedback {

    ContributionFeedback saveContributionFeedback(ContributionFeedback contributionFeedback);

    ContributionFeedback getContributionFeedback(String id);

    ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory, int groupId);

    void updateContributionFeedback(ContributionFeedback contributionFeedback);

    void endFeedback(Task task);
}
