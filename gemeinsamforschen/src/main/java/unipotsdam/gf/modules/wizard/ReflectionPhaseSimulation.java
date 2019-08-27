package unipotsdam.gf.modules.wizard;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.modules.reflection.model.SelectedReflectionQuestion;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionAnswersDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.modules.reflection.service.SelectedLearningGoalsDAO;
import unipotsdam.gf.modules.reflection.service.SelectedReflectionQuestionsDAO;
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
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static unipotsdam.gf.modules.wizard.Wizard.convertTextToQuillJs;

public class ReflectionPhaseSimulation implements IReflectionPhaseSimulation {

    //public static Boolean FEEDBACK_IMPLEMENTED = false;


    private final static Logger log = LoggerFactory.getLogger(CommunicationService.class);

    private final TomcatConceptImporter concepts;

    @Inject
    UserDAO userDAO;

    @Inject
    TaskDAO taskDAO;

    @Inject
    GroupDAO groupDAO;

    @Inject
    WizardDao wizardDao;

    @Inject
    private IExecutionProcess iExecutionProcess;

    @Inject
    private LearningGoalStoreDAO learningGoalStoreDAO;

    @Inject
    private ReflectionQuestionsStoreDAO reflectionQuestionsStoreDAO;

    @Inject
    private SelectedLearningGoalsDAO selectedLearningGoalsDAO;

    @Inject
    private SelectedReflectionQuestionsDAO selectedReflectionQuestionsDAO;

    @Inject
    private ReflectionQuestionAnswersDAO reflectionQuestionAnswersDAO;

    @Inject
    private SubmissionController submissionController;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    private LoremIpsum loremIpsum;

    public ReflectionPhaseSimulation() throws UnsupportedEncodingException {
        loremIpsum = new LoremIpsum();
        this.concepts = new TomcatConceptImporter();
    }

    @Override
    public void simulateQuestionSelection(Project project) throws Exception {

        List<Task> taskForProject = taskDAO.getTaskForProjectByTaskName(project,
                TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS);
        if (taskForProject.get(0).getProgress() != Progress.FINISHED) {
            List<LearningGoalStoreItem> allStoreGoals = learningGoalStoreDAO.getAllStoreGoals();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                int y = random.nextInt(allStoreGoals.size());
                LearningGoalStoreItem learningGoalStoreItem = allStoreGoals.get(y);
                allStoreGoals.remove(y);
                // create the request
                LearningGoalRequest learningGoalRequest = new LearningGoalRequest();
                learningGoalRequest.setLearningGoal(learningGoalStoreItem);
                learningGoalRequest.setProjectName(project.getName());
                for (int z = 0; z < 3; z++) {
                    List<ReflectionQuestionsStoreItem> learningGoalSpecificQuestions =
                            reflectionQuestionsStoreDAO
                                    .getLearningGoalSpecificQuestions(learningGoalStoreItem.getText());
                    int questionIndex = random.nextInt(learningGoalSpecificQuestions.size());
                    ReflectionQuestionsStoreItem reflectionQuestionsStoreItem =
                            learningGoalSpecificQuestions.get(questionIndex);
                    String question = reflectionQuestionsStoreItem.getQuestion();
                    reflectionQuestionsStoreItem.setQuestion(question + String.format("(ID: %s%s)", y, z));
                    learningGoalRequest.getReflectionQuestions().add(reflectionQuestionsStoreItem);
                }
                iExecutionProcess.selectLearningGoalAndReflectionQuestions(learningGoalRequest);
            }
            iExecutionProcess.finalizeLearningGoalsAndReflectionQuestionsSelection(project);
        }
        /*else {
            iExecutionProcess.start(project);
            simulateQuestionSelection(project);
        }*/
    }

    @Override
    public void simulateCreatingPortfolioEntries(Project project) throws Exception {
        //if (submissionController.getProjectSubmissions(project, FileRole.PORTFOLIO_ENTRY, ))
        simulateSubmissions(project, FileRole.PORTFOLIO_ENTRY, Visibility.GROUP);
    }

    @Override
    public void simulateDocentFeedback(Project project) {
        //TODO implement
    }

    @Override
    public void simulateChoosingPortfolioEntries(Project project) throws Exception {
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {
            List<FullSubmission> portfolioEntries =
                    submissionController.getAssessableSubmissions(user, project, FileRole.PORTFOLIO_ENTRY);
            List<FullSubmission> groupEntries = portfolioEntries.stream()
                    .filter(entry -> entry.getVisibility() == Visibility.GROUP
                            || entry.getVisibility() == Visibility.PUBLIC).sorted().collect(Collectors.toList());
            if (!groupEntries.isEmpty()) {
                Random random = new Random();
                int numberselected = random.nextInt(groupEntries.size() - 1);
                List<FullSubmission> fullSubmissions = groupEntries.subList(0, numberselected);
                iExecutionProcess.selectPortfolioEntries(project, user, fullSubmissions);

            }
        }
    }

    @Override
    public void simulateAnsweringReflectiveQuestions(Project project) throws Exception {
        if (!wizardDao.reflectiveQuestionsAreAnswered(project)) {
            FileRole rq = FileRole.REFLECTION_QUESTION;
            simulateSubmissions(project, rq, Visibility.DOCENT);
        }
    }

    private void simulateSubmissions(Project project, FileRole fileRole, Visibility visibility) throws Exception {
        if (submissionController.getProjectSubmissions(project, fileRole, visibility).size() < 2) {
            List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
            for (User user : usersByProjectName) {
                switch (fileRole) {
                    case PORTFOLIO_ENTRY:
                        List<FullSubmission> assessableSubmissions =
                                submissionController.getAssessableSubmissions(user, project, fileRole);
                        if (CollectionUtils.isEmpty(assessableSubmissions)) {
                            List<SelectedReflectionQuestion> reflectionQuestions = selectedReflectionQuestionsDAO.findBy(project);
                            createEntries(project, user, reflectionQuestions, fileRole, visibility);
                        }
                        break;
                    case REFLECTION_QUESTION:
                        List<SelectedReflectionQuestion> unansweredQuestions =
                                selectedReflectionQuestionsDAO.getUnansweredQuestions(project, user, false);
                        createEntries(project, user, unansweredQuestions, fileRole, visibility);
                        break;
                }
            }
        }
    }

    private void createEntries(Project project, User user, List<SelectedReflectionQuestion> reflectionQuestions,
                               FileRole fileRole, Visibility visibility) throws Exception {
        if (user == null || user.getEmail() == null) {
            log.error("cannot create entries for user null" + project.getName());
        } else {
            if (reflectionQuestions != null) {
                for (SelectedReflectionQuestion reflectionQuestion : reflectionQuestions) {
                    Group myGroup = groupDAO.getMyGroup(user, project);
                    String text = loremIpsum.getWords(500);
                    text = convertTextToQuillJs(text);
                    String title = concepts.getNumberedConcepts(3).stream().reduce((x, y) -> x + " " + y).orElse("Default");
                    FullSubmissionPostRequest submission =
                            new FullSubmissionPostRequest(myGroup, text, fileRole, project, visibility, title);
                    submission.setHtml(text);
                    if (fileRole == FileRole.REFLECTION_QUESTION) {
                        submission.setSaveUsername(true);
                        submission.setUserEMail(user.getEmail());
                        submission.setReflectionQuestionId(reflectionQuestion.getId());
                    }
                    FullSubmission fullSubmission = dossierCreationProcess.addDossier(submission, user, project);
                    if (fileRole == FileRole.REFLECTION_QUESTION) {
                        iExecutionProcess.answerReflectionQuestion(fullSubmission, reflectionQuestion);
                    }
                }
            }
        }
    }

}
