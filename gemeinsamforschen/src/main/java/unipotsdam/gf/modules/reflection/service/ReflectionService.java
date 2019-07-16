package unipotsdam.gf.modules.reflection.service;

import com.google.common.base.Strings;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
public class ReflectionService implements IReflection {

    @Inject
    private LearningGoalsDAO learningGoalsDAO;

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private LearningGoalStudentResultsDAO learningGoalStudentResultsDAO;

    public LearningGoalRequestResult createLearningGoalWithQuestions(LearningGoalRequest learningGoalRequest) {
        Project project = new Project(learningGoalRequest.getProjectName());
        LearningGoal learningGoal = new LearningGoal(learningGoalRequest.getLearningGoal(), project);
        String learningGoalUuid = learningGoalsDAO.persist(learningGoal);
        if (Strings.isNullOrEmpty(learningGoalUuid)) {
            return null;
        }
        learningGoal.setId(learningGoalUuid);
        List<User> users = userDAO.getUsersByProjectName(learningGoalRequest.getProjectName());
        LearningGoalRequestResult learningGoalRequestResult = new LearningGoalRequestResult();
        learningGoalRequestResult.setLearningGoal(learningGoal);
        users.forEach(user -> {
            learningGoalRequest.getReflectionQuestions().forEach(storeItem -> {
                ReflectionQuestion reflectionQuestion = new ReflectionQuestion(storeItem, user, project, learningGoal);
                String questionUuid = reflectionQuestionDAO.persist(reflectionQuestion);
                reflectionQuestion.setId(questionUuid);
                learningGoalRequestResult.getReflectionQuestions().add(reflectionQuestion);
            });
        });
        return learningGoalRequestResult;
    }

    public LearningGoalStudentResult saveStudentResult(LearningGoalStudentResult studentResult, User user) {
        if (Strings.isNullOrEmpty(user.getEmail())) {
            return null;
        }
        studentResult.setUserEmail(user.getEmail());
        String uuid = learningGoalStudentResultsDAO.persist(studentResult);
        studentResult.setId(uuid);
        return studentResult;
    }

}
