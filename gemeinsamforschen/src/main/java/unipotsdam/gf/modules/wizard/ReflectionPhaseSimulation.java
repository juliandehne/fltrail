package unipotsdam.gf.modules.wizard;

import de.svenjacobs.loremipsum.LoremIpsum;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.LearningGoalsDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.compbase.TomcatConceptImporter;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static unipotsdam.gf.modules.wizard.Wizard.convertTextToQuillJs;

public class ReflectionPhaseSimulation {
    
    @Inject
    private IExecutionProcess iExecutionProcess;

    @Inject
    private LearningGoalStoreDAO learningGoalStoreDAO;

    @Inject
    private ReflectionQuestionsStoreDAO reflectionQuestionsStoreDAO;

    @Inject
    private LearningGoalsDAO learningGoalsDAO;

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private SubmissionController submissionController;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @Inject
    UserDAO userDAO;

    @Inject
    TaskDAO taskDAO;

    @Inject
    GroupDAO groupDAO;

    private final TomcatConceptImporter concepts;
    private LoremIpsum loremIpsum;

    public static Boolean FEEDBACK_IMPLEMENTED = false;

    public ReflectionPhaseSimulation() throws UnsupportedEncodingException {
        loremIpsum = new LoremIpsum();
        this.concepts = new TomcatConceptImporter();
    }

    public void simulateQuestionSelection(Project project) throws Exception {

        List<Task> taskForProject =
                taskDAO.getTaskForProjectByTaskName(project, TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS);
        if (taskForProject != null && taskForProject.size() > 0) {
            Task task = taskForProject.iterator().next();
            if (task.getProgress().equals(Progress.FINISHED)) {
                // mayb logging
                System.out.println("hihi");
            } else {
                List<LearningGoalStoreItem> allStoreGoals = learningGoalStoreDAO.getAllStoreGoals();
                List<LearningGoalStoreItem> selectedLearningGoals = new ArrayList<>();
                List<ReflectionQuestionsStoreItem> reflectionQuestionsStoreItems = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
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
                                reflectionQuestionsStoreDAO
                                .getLearningGoalSpecificQuestions(learningGoalStoreItem.getText());
                        int questionIndex = random.nextInt(learningGoalSpecificQuestions.size());
                        //
                        ReflectionQuestionsStoreItem reflectionQuestionsStoreItem =
                                learningGoalSpecificQuestions.get(questionIndex);
                        reflectionQuestionsStoreItems.add(reflectionQuestionsStoreItem);
                        learningGoalRequest.getReflectionQuestions().add(reflectionQuestionsStoreItem);
                    }
                    iExecutionProcess.saveLearningGoalsAndReflectionQuestions(learningGoalRequest);
                }
            }
        }

        /*else {
            iExecutionProcess.start(project);
            simulateQuestionSelection(project);
        }*/
    }

    public void simulateCreatingPortfolioEntries(Project project) throws Exception {
            simulateSubmissions(project, FileRole.PORTFOLIO_ENTRY, Visibility.GROUP);
    }

    public void simulateDocentFeedback(Project project) {
        //TODO implement
    }

    public void simulateChooseingPortfolioEntries(Project project) throws Exception {
        List<FullSubmission> selectedGroupPortfolioEntries =
                submissionController.getSelectedGroupPortfolioEntries(project);
        if (selectedGroupPortfolioEntries == null || selectedGroupPortfolioEntries.isEmpty()) {
            List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
            for (User user : usersByProjectName) {
                // TODO @Martin plz refactor this
                List<FullSubmission> portfolioEntries =
                        submissionController.getPersonalSubmissions(user, project, FileRole.PORTFOLIO_ENTRY);
                List<FullSubmission> groupEntries = portfolioEntries.stream()
                        .filter(entry -> entry.getVisibility() == Visibility.GROUP
                                || entry.getVisibility() == Visibility.PUBLIC).sorted().collect(Collectors.toList());
                Random random = new Random();
                int numberselected = random.nextInt(groupEntries.size() -1 );
                List<FullSubmission> fullSubmissions = groupEntries.subList(0, numberselected);
                iExecutionProcess.selectPortfolioEntries(project, user, fullSubmissions);
            }
        }
    }

    public void simulateAnsweringReflectiveQuestions(Project project) throws Exception {
        FileRole rq = FileRole.REFLECTION_QUESTION;
        simulateSubmissions(project, rq, Visibility.DOCENT);
        
    }

    private void simulateSubmissions(Project project, FileRole rq, Visibility visibility) throws Exception {
        List<FullSubmission> projectSubmissions =
                submissionController.getProjectSubmissions(project, rq, visibility);
        if (projectSubmissions == null || projectSubmissions.isEmpty()) {
            List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
            for (User user : usersByProjectName) {
                List<ReflectionQuestion> reflectionQuestions =
                        reflectionQuestionDAO.getUnansweredQuestions(project, user, false);
                if (reflectionQuestions != null) {
                    for (ReflectionQuestion reflectionQuestion : reflectionQuestions) {
                        Group myGroup = groupDAO.getMyGroup(user, project);
                        String text = loremIpsum.getWords(500);
                        text = convertTextToQuillJs(text);
                        String title = concepts.getNumberedConcepts(3).stream().reduce((x, y) -> x + " " + y).get();
                        FullSubmissionPostRequest submission =
                                new FullSubmissionPostRequest(myGroup, text, rq, project, visibility, title);
                        submission.setHtml(text);
                        FullSubmission fullSubmission = dossierCreationProcess.addDossier(submission, user, project);
                        submission.setId(fullSubmission.getId());
                        iExecutionProcess.answerReflectionQuestion(fullSubmission, reflectionQuestion);
                        // dossier creation processs is used as substitute -- needs refactoring
                        //dossierCreationProcess.addDossier(submission, user, project);
                    }
                }
            }
        }
    }

}
