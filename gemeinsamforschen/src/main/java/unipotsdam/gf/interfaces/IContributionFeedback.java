package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;

public interface IContributionFeedback {

    ContributionFeedback saveContributionFeedback(ContributionFeedback contributionFeedback);

    ContributionFeedback getContributionFeedback(String id);

    ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory, int groupId);

    void updateContributionFeedback(ContributionFeedback contributionFeedback);

    void endFeedback(String projectName, int groupId);
}
