package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.modules.user.User;

public interface IReflection {

    LearningGoalRequestResult createLearningGoalWithQuestions(LearningGoalRequest learningGoalRequest);

    LearningGoalStudentResult saveStudentResult(LearningGoalStudentResult studentResult, User user);
}
