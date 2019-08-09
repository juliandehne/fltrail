package unipotsdam.gf.modules.wizard;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.LearningGoalsDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ReflectionPhaseSimulation {

    @Inject
    private IExecutionProcess iExecutionProcess;

    @Inject
    private LearningGoalStoreDAO learningGoalStoreDAO;

    @Inject
    private ReflectionQuestionsStoreDAO reflectionQuestionsStoreDAO;

    @Inject
    private LearningGoalsDAO learningGoalsDAO;

    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private SubmissionController submissionController;

    @Inject
    UserDAO userDAO;

    @Inject
    TaskDAO taskDAO;

    public void simulateQuestionSelection(Project project) throws Exception {

        List<Task> taskForProject =
                taskDAO.getTaskForProject(project, TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS);
        if (taskForProject.size() > 0) {
            Task task = taskForProject.iterator().next();
            if (task.getProgress().equals(Progress.FINISHED)) {
                // mayb logging
            } else {
                List<LearningGoalStoreItem> allStoreGoals = learningGoalStoreDAO.getAllStoreGoals();
                List<LearningGoalStoreItem> selectedLearningGoals = new ArrayList<>();
                List<ReflectionQuestionsStoreItem> reflectionQuestionsStoreItems = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i< 10; i++) {
                    int y = random.nextInt(allStoreGoals.size());
                    LearningGoalStoreItem learningGoalStoreItem = allStoreGoals.get(y);
                    selectedLearningGoals.add(learningGoalStoreItem);
                    // create the request
                    LearningGoalRequest learningGoalRequest = new LearningGoalRequest();
                    learningGoalRequest.setLearningGoal(learningGoalStoreItem);
                    learningGoalRequest.setProjectName(project.getName());
                    // it should finalize with the last:
                    learningGoalRequest.setEndTask(i == 9);
                    for (int z = 0; z < 3; z++) {
                        List<ReflectionQuestionsStoreItem> learningGoalSpecificQuestions =
                                reflectionQuestionsStoreDAO.getLearningGoalSpecificQuestions(learningGoalStoreItem.getText());
                        int questionIndex = random.nextInt(2);
                        ReflectionQuestionsStoreItem reflectionQuestionsStoreItem =
                                learningGoalSpecificQuestions.get(questionIndex);
                        reflectionQuestionsStoreItems.add(reflectionQuestionsStoreItem);
                        learningGoalRequest.getReflectionQuestions().add(reflectionQuestionsStoreItem);
                        ;
                    }
                    iExecutionProcess.saveLearningGoalsAndReflectionQuestions(learningGoalRequest);
                }
            }
        }
    }


    public void simulateCreatingPortfolioEntries(Project project) {
        // todo implement
    }

    public void simulateDocentFeedback(Project project) {

        //TODO implement
    }

    public void simulateChooseingPortfolioEntries(Project project) throws Exception {
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {

            List<FullSubmission> personalSubmissions =
                    submissionController.getPersonalSubmissions(user, project, FileRole.PORTFOLIO_ENTRY);

            // TODO @Martin plz refactor this
            List<FullSubmission> portfolioEntries = submissionController.getPersonalSubmissions(user, project, FileRole.PORTFOLIO_ENTRY);
            List<FullSubmission> groupEntries = portfolioEntries.stream()
                    .filter(entry -> entry.getVisibility() == Visibility.GROUP || entry.getVisibility() == Visibility.PUBLIC)
                    .sorted().collect(Collectors.toList());
            Random random = new Random();
            int numberselected = random.nextInt(2);
            List<FullSubmission> fullSubmissions = groupEntries.subList(0, numberselected);
            iExecutionProcess.selectPortfolioEntries(project, user, fullSubmissions);
        }


    }

    public void simulateAnsweringReflectiveQuestions(Project project) {

    }

}
