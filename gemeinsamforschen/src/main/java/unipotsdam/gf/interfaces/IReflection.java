package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionWithAnswer;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;

import java.util.List;

public interface IReflection {

    LearningGoalRequestResult selectLearningGoalAndReflectionQuestion(LearningGoalRequest learningGoalRequest);

    List<LearningGoalRequestResult> getSelectedLearningGoalsAndReflectionQuestions(Project project);

    void persistReflectionQuestionsToAnswer(Project project);

    List<FullSubmission> getGroupAndPublicVisiblePortfolioEntriesByUser(User user, Project project);

    List<ReflectionQuestionWithAnswer> getAnsweredReflectionQuestions(Project project);

    List<ReflectionQuestionWithAnswer> getAnsweredReflectionQuestionsFromUser(Project project, User user);
}
