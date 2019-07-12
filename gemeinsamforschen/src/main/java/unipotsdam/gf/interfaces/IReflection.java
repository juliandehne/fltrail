package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalResult;

public interface IReflection {

    LearningGoalResult createLearningGoalWithQuestions(LearningGoalRequest learningGoalRequest);
}
