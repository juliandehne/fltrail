package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;

public interface IReflection {

    LearningGoalRequestResult createLearningGoalWithQuestions(LearningGoalRequest learningGoalRequest);
}
