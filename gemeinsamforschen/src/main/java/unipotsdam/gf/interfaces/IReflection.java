package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;

import java.util.List;

public interface IReflection {

    LearningGoalRequestResult createLearningGoalWithQuestions(LearningGoalRequest learningGoalRequest);

    List<FullSubmission> getGroupAndPublicVisiblePortfolioEntriesByUser(User user, Project project);
}
