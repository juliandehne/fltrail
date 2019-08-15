package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;

import java.util.List;

public interface IContributionFeedback {

    ContributionFeedback saveContributionFeedback(ContributionFeedback contributionFeedback) throws Exception;

    ContributionFeedback getContributionFeedback(String id);

    ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory, int groupId);

    ContributionFeedback getContributionFeedback(String fullSubmissionId, String fullSubmissionCategory);

    List<ContributionFeedback> getContributionFeedbacksForFullSubmission(String fullSubmissionId);

    void updateContributionFeedback(ContributionFeedback contributionFeedback);

    void endFeedback(String projectName, int groupId);
}
