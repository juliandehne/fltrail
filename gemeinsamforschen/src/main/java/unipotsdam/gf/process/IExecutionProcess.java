package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.SelectedReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;

import java.util.List;

public interface IExecutionProcess {
    void start(Project project);

    LearningGoalRequestResult selectLearningGoalAndReflectionQuestions(LearningGoalRequest learningGoalRequest);

    void finalizeLearningGoalsAndReflectionQuestionsSelection(Project project) throws Exception;

    void answerReflectionQuestion(FullSubmission fullSubmission, SelectedReflectionQuestion reflectionQuestion) throws Exception;

    void getDocentFeedback(FullSubmission fullSubmission) throws Exception;

    void selectPortfolioEntries(Project project, User user, List<FullSubmission> selectedPortfolioEntries) throws Exception;

    void saveGroupSubmissionPdf(Project project, int groupId, String html) throws Exception;

    boolean isPhaseCompleted(Project project);

    void finishPhase(Project project) throws Exception;
}
