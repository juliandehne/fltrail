package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;

public interface IExecutionProcess {
    void start(Project project);

    void startLearningGoalPeriod(Project project) throws Exception;

    void finishLearningGoalPeriod(Project project) throws Exception;

    LearningGoalRequestResult saveLearningGoalsAndReflectionQuestions(LearningGoalRequest learningGoalRequest) throws Exception;

    LearningGoalStudentResult uploadLearningGoalResult(LearningGoalStudentResult studentResult, User user) throws Exception;

    void answerReflectionQuestion(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) throws Exception;

    void chooseAssessmentMaterial(Project project, User user, String html) throws Exception;

    boolean isPhaseCompleted(Project project);

    void finishPhase(Project project) throws Exception;
}
