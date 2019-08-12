package unipotsdam.gf.modules.reflection.service;

import com.google.common.base.Strings;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
public class ReflectionService implements IReflection {

    @Inject
    private LearningGoalsDAO learningGoalsDAO;

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private SubmissionController submissionController;

    @Override
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

    @Override
    public List<FullSubmission> getGroupAndPublicVisiblePortfolioEntriesByUser(User user, Project project) {
        List<FullSubmission> portfolioEntries = submissionController.getPersonalSubmissions(user, project, FileRole.PORTFOLIO_ENTRY);
        return portfolioEntries.stream()
                .filter(entry -> entry.getVisibility() == Visibility.GROUP || entry.getVisibility() == Visibility.PUBLIC)
                .sorted().collect(Collectors.toList());
    }

}
