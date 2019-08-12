package unipotsdam.gf.modules.wizard;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.FLTrailConfig;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.*;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.wizard.compbase.TomcatConceptImporter;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class Wizard {

    private final static Logger log = LoggerFactory.getLogger(Wizard.class);

    private final TomcatConceptImporter concepts;

    @Inject
    private WizardDao wizardDao;

    @Inject
    private IPhases phases;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private ProjectCreationProcess projectCreationProcess;

    @Inject
    private GroupFormationProcess groupFormationProcess;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private Management management;

    @Inject
    private DossierCreationProcess dossierCreationProcess;


    @Inject
    SubmissionController submissionController;

    @Inject
    PeerAssessmentSimulation peerAssessmentSimulation;

    @Inject
    ReflectionPhaseSimulation reflectionPhaseSimulation;

    @Inject
    Feedback feedback;


    private LoremIpsum loremIpsum;
    private PodamFactoryImpl factory = new PodamFactoryImpl();

    public Wizard() throws UnsupportedEncodingException {

        this.concepts = new TomcatConceptImporter();
        this.loremIpsum = new LoremIpsum();
    }

    /**
     * closes all previous tasks
     *
     * @param taskName that is about to get closed
     * @param project  of interest
     */
    void doSpells(TaskName taskName, Project project) throws Exception {

        // simulate previous tasks
        Phase correspondingPhase2 = phases.getCorrespondingPhase(taskName);
        simulatePreviousPhases(correspondingPhase2, project);

        // simulate tasks in this phase
        simulateTasksInThisPhase(taskName, project);
    }

    private void simulateTasksInThisPhase(TaskName taskName, Project project) throws Exception {
        Phase correspondingPhase = phases.getCorrespondingPhase(taskName);

        // get previous tasks including the current
        List<TaskName> previousTasks = getPreviousTasks(taskName);
        //List<Phase> previousPhases = phases.getFinishedPhases(correspondingPhase, project);
        List<Phase> previousPhases = phases.getPreviousPhases(correspondingPhase);
        // simulate current phase up to task
        HashSet<TaskName> previousTasksNotInThisPhase = new HashSet<>();
        for (Phase previousPhase : previousPhases) {
            List<TaskName> taskNames = phases.getTaskNames(previousPhase);
            previousTasksNotInThisPhase.addAll(taskNames);
        }
        // removing all tasks that have been dealt with in previous phases and have been simulated already
        previousTasks.removeAll(previousTasksNotInThisPhase);
        // do simulations for previous dealts
        simulatePreviousTasks(project, previousTasks);
    }

    void doSpells(Phase phase, Project project) throws Exception {
        simulatePreviousPhases(phase, project);
        // simulate the current phase
        simulatePhase(project, phase);
    }

    private void simulatePreviousTasks(Project project, List<TaskName> previousTasks) throws Exception {
        // previous tasks only contains tasks in this phase now
        for (TaskName name : previousTasks) {
            switch (name) {
                case WAIT_FOR_PARTICPANTS: {
                    ProjectStatus participantCount = projectDAO.getParticipantCount(project);
                    if (participantCount.getParticipants() < 6) {
                        createStudents(project, participantCount);
                    }
                    break;
                }
                case UPLOAD_DOSSIER:
                    createDossiers(project);
                    break;
                case ANNOTATE_DOSSIER:
                    annotateDossiers(project);
                    break;
                case GIVE_FEEDBACK: {
                    generateFeedbacks(project);
                    break;
                }
                case REEDIT_DOSSIER:
                    finalizeDossiers(project);
                    break;
                case WAIT_FOR_REFLECTION_QUESTION_CHOICE:
                    reflectionPhaseSimulation.simulateQuestionSelection(project);
                    break;
                case WIZARD_CREATE_PORTFOLIO:
                    reflectionPhaseSimulation.simulateCreatingPortfolioEntries(project);
                    break;
                case DOCENT_GIVE_PORTOLIO_FEEDBACK:
                    reflectionPhaseSimulation.simulateDocentFeedback(project);
                    break;
                case CHOOSE_PORTFOLIO_ENTRIES:
                    reflectionPhaseSimulation.simulateChoosingPortfolioEntries(project);
                    break;
                case ANSWER_REFLECTION_QUESTIONS:
                    reflectionPhaseSimulation.simulateAnsweringReflectiveQuestions(project);
                case UPLOAD_PRESENTATION:
                    generatePresentationsForAllGroupsAndUploadThem(project);
                    break;
                case UPLOAD_FINAL_REPORT:
                    generateFinalReportsForAllGroupsAndUploadThem(project);
                    break;
                case GIVE_EXTERNAL_ASSESSMENT:
                    externalPeerAssessments(project);
                    break;
                case GIVE_INTERNAL_ASSESSMENT:
                    internalPeerAssessments(project);
                    break;
                case GIVE_EXTERNAL_ASSESSMENT_TEACHER:
                    docentAssessments(project);
                    break;
            }
        }
    }

    private void simulatePhase(Project project, Phase phase) throws Exception {
        // phase will be ended as a call to phases, at the end in any case
        switch (phase) {
            case GroupFormation:
                ProjectStatus participantCount = projectDAO.getParticipantCount(project);
                createStudents(project, participantCount);
                break;
            case DossierFeedback: {
                createDossiers(project);
                annotateDossiers(project);
                generateFeedbacks(project);
                finalizeDossiers(project);
                break;
            }
            case Execution: {
                reflectionPhaseSimulation.simulateQuestionSelection(project);
                reflectionPhaseSimulation.simulateCreatingPortfolioEntries(project);
                reflectionPhaseSimulation.simulateDocentFeedback(project);
                reflectionPhaseSimulation.simulateChoosingPortfolioEntries(project);
                reflectionPhaseSimulation.simulateAnsweringReflectiveQuestions(project);
                finalizeReflection(project);
                break;
            }
            case Assessment: {
                generatePresentationsForAllGroupsAndUploadThem(project);
                generateFinalReportsForAllGroupsAndUploadThem(project);
                externalPeerAssessments(project);
                internalPeerAssessments(project);
                break;
            }
            case GRADING: {
                docentAssessments(project);
                break;
            }
            case Projectfinished: {

            }
        }
        // finish tasks in the corresponding phase
        List<TaskName> taskNames = phases.getTaskNames(phase);
        for (TaskName taskName : taskNames) {
            taskDAO.updateForAll(new Task(taskName, null, project, Progress.FINISHED));
        }
        // finish phase this might duplicate finishing the tasks
        phases.endPhase(phase, project, new User(project.getAuthorEmail()));
    }


    private void simulatePreviousPhases(Phase correspondingPhase, Project project) throws Exception {
        List<Phase> previousPhases = phases.getFinishedPhases(correspondingPhase, project);
        for (Phase previousPhase : previousPhases) {
            simulatePhase(project, previousPhase);
        }
    }

    private List<TaskName> getPreviousTasks(TaskName taskName) {
        /*if (taskName.equals(TaskName.WAIT_FOR_PARTICPANTS)) {
            return ;
        }*/
        TaskOrder taskOrder = new TaskOrder();
        List<TaskName> orderedTasks = taskOrder.getOrderedTasks();
        int i = orderedTasks.indexOf(taskName);
        return orderedTasks.subList(0, i + 1);
    }

    public static String convertTextToQuillJs(String text) throws IOException {
        HashMap<String, String> quillJsContents = new HashMap<>();
        quillJsContents.put("insert", text);
        ArrayList<HashMap<String, String>> lArray = new ArrayList<>();
        lArray.add(quillJsContents);
        lArray.add(quillJsContents);
        HashMap<String, ArrayList<HashMap<String, String>>> lObject = new HashMap<>();
        lObject.put("ops", lArray);
        ObjectMapper mapper = new ObjectMapper();
        text = mapper.writeValueAsString(lObject);
        return text;
    }

    public void createMockDataForCompBase(Project project, User student) throws Exception {
        PreferenceData preferenceData = factory.manufacturePojo(PreferenceData.class);
        // check if tags were persisted for this project
        ArrayList<String> tags = getOrPersistTags(project);
        // if not persist new ones
        String prefix = "Studierende interessieren sich für ";
        preferenceData.setTagsSelected(new ArrayList<>());
        preferenceData.getTagsSelected().add(prefix + tags.get(1));
        preferenceData.getTagsSelected().add(prefix + tags.get(2));

        List<String> concepts = this.concepts.getNumberedConcepts(5);
        preferenceData.getCompetences().clear();
        for (int i = 0; i < 5; i++) {
            String competence = prefix + concepts.get(i);
            preferenceData.getCompetences().add(competence);
        }
        preferenceData.setResearchQuestions(new ArrayList<>());
        groupFormationProcess.sendCompBaseUserData(project, student, preferenceData);
    }

    private ArrayList<String> getOrPersistTags(Project project) {
        List<String> tags = projectDAO.getTags(project);

        if (tags == null || tags.isEmpty()) {
            tags = concepts.getNumberedConcepts(5);
            projectDAO.persistTagsForWizard(project, tags);
        }
        return new ArrayList<>(tags);
    }

    private void createMockDataForGroupal(Project project, User user) {
        HashMap<String, String> data = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int questionId = random.nextInt(26) + 1;
            int rating = random.nextInt(4) + 1;
            data.put(questionId + "", rating + "");
        }
        groupFormationProcess.sendGroupAlDataToServer(data, user, project);
    }

    private void createStudents(Project project, ProjectStatus participantCount) throws Exception {
        Random random = new Random();

        //IntStream.range(0, participantCount.getParticipants()).boxed().parallel().forEach(z->log.info(z.toString()));

        int neededParticipantCount = 30 - participantCount.getParticipants();

        // dont ask Axel
        List<User> students = IntStream.rangeClosed(0, neededParticipantCount).boxed().parallel()
                .map(x -> createUserParallel(project, random)).filter(Objects::nonNull).collect(Collectors.toList());

        if (FLTrailConfig.wizardSimulatesFullAlgorithms) {
            for (User student : students) {
                GroupFormationMechanism groupMechanismSelected =
                        management.getProjectConfiguration(project).getGroupMechanismSelected();
                switch (groupMechanismSelected) {
                    case UserProfilStrategy:
                        // mock compbase data is generated
                        createMockDataForGroupal(project, student);
                        break;
                    case LearningGoalStrategy:
                        // mock groupal data generation in case manual group formation is tested
                        createMockDataForCompBase(project, student);
                        break;
                }
            }
        } else {
            groupFormationProcess.changeGroupFormationMechanism(GroupFormationMechanism.Manual, project);
        }
        //groupFormationProcess.getOrInitializeGroups(project);

    }

    private User createUserParallel(Project project, Random random) {
        User user = factory.manufacturePojo(User.class);
        try {

            user.setStudent(true);

            user.setRocketChatUsername("studentwizard" + random.nextInt(1000000));
            user.setEmail("studentwizard" + random.nextInt(1000000) + "@stuff.com");
            user.setPassword("egal");
            projectCreationProcess.deleteUser(user);
            projectCreationProcess.createUser(user);
            projectCreationProcess.studentEntersProject(project, user);
            return user;
        } catch (Exception e) {
            System.out.println(e);
            // might have been a problem with UUID generation should not crash
        }
        return user;
    }

    private void annotateDossiers(Project project) {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            User representativUser = groupDAO.getRepresentativUser(group, project);
            Task annotateDossierTask = taskDAO.getTasksWithTaskName(group.getId(), project, TaskName.ANNOTATE_DOSSIER);
            if (annotateDossierTask == null || annotateDossierTask.getProgress() != Progress.FINISHED) {
                FullSubmission fullSubmission =
                        submissionController.getFullSubmissionBy(group.getId(), project, FileRole.DOSSIER);
                List<String> annotationCategories = submissionController.getAnnotationCategories(project);
                int startCharacter = 0;
                for (String category : annotationCategories) {
                    ArrayList<SubmissionPartBodyElement> spbe = new ArrayList<>();
                    spbe.add(new SubmissionPartBodyElement("", startCharacter,
                            startCharacter + fullSubmission.getText().length() / annotationCategories.size() - 1));
                    SubmissionPartPostRequest sppr =
                            new SubmissionPartPostRequest(group.getId(), fullSubmission.getId(), category, spbe);
                    submissionController.addSubmissionPart(sppr);
                    startCharacter = startCharacter + fullSubmission.getText().length() / annotationCategories.size();
                }
                dossierCreationProcess.finalizeDossier(fullSubmission, project, representativUser);
            }
        }
        //if (submissionController.get)
    }

    private void createDossiers(Project project) throws IOException {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            List<Group> allGroupsWithDossierUploaded = submissionController.getAllGroupsWithDossierUploaded(project);
            if (!allGroupsWithDossierUploaded.contains(group)) {
                User representativUser = groupDAO.getRepresentativUser(group, project);
                String text = loremIpsum.getWords(500);
                text = convertTextToQuillJs(text);
                String title = concepts.getNumberedConcepts(3).stream().reduce((x, y) -> x + " " + y).get();
                FullSubmissionPostRequest submission =
                        new FullSubmissionPostRequest(group, text, FileRole.DOSSIER, project, Visibility.PUBLIC, title);
                submission.setHtml(text);
                FullSubmission fullSubmission =
                        dossierCreationProcess.addDossier(submission, representativUser, project);
                submission.setId(fullSubmission.getId());
            }
        }
    }

    private void generateFeedbacks(Project project) throws IOException {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            User representativUser = groupDAO.getRepresentativUser(group, project);
            int feedbackTarget = feedback.getFeedBackTarget(project, representativUser);
            if (taskDAO.getTasksWithTaskName(feedbackTarget, project, TaskName.SEE_FEEDBACK) == null) {
                //group writes a feedback
                FullSubmission fullSubmission =
                        submissionController.getFullSubmissionBy(feedbackTarget, project, FileRole.DOSSIER, 0);
                List<String> annotationCategories = submissionController.getAnnotationCategories(project);
                for (String category : annotationCategories) {
                    String text = loremIpsum.getWords(20);
                    text = convertTextToQuillJs(text);
                    ContributionFeedback contributionFeedback =
                            new ContributionFeedback(group.getId(), fullSubmission.getId(), category, text);
                    contributionFeedback.setUserEmail(representativUser.getEmail());
                    dossierCreationProcess.saveFeedback(contributionFeedback);
                }
                dossierCreationProcess.saveFinalFeedback(group.getId(), project);
            }
        }
    }

    private void finalizeDossiers(Project project) throws Exception {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            User representativUser = groupDAO.getRepresentativUser(group, project);
            FullSubmission fullSubmission =
                    submissionController.getFullSubmissionBy(group.getId(), project, FileRole.DOSSIER, 1);
            FullSubmissionPostRequest fullSubmissionPostRequest =
                    new FullSubmissionPostRequest(group, fullSubmission.getText(), fullSubmission.getFileRole(),
                            project, fullSubmission.getVisibility(), fullSubmission.getHeader());
            fullSubmissionPostRequest.setHtml(fullSubmission.getText());
            fullSubmissionPostRequest.setUserEMail(representativUser.getEmail());
            dossierCreationProcess.updateSubmission(fullSubmissionPostRequest, representativUser, project, true);
        }
        if (project.getAuthorEmail() == null) {
            throw new Exception("no author set!!");
        }
        //phases.endPhase(Phase.DossierFeedback, project, new User(project.getAuthorEmail()));
    }

    private void finalizeReflection(Project project) throws Exception {
        if (project.getAuthorEmail() == null) {
            throw new Exception("no author set!!");
        }
        phases.endPhase(Phase.Execution, project, new User(project.getAuthorEmail()));
    }

    private void generatePresentationsForAllGroupsAndUploadThem(Project project)
            throws CssResolverException, DocumentException, IOException {
        peerAssessmentSimulation.generatePresentationsForAllGroupsAndUploadThem(project);
    }

    private void generateFinalReportsForAllGroupsAndUploadThem(Project project) throws Exception {
        peerAssessmentSimulation.generateFinalReportsForAllGroupsAndUploadThem(project);
    }

    private void externalPeerAssessments(Project project) throws Exception {
        peerAssessmentSimulation.externalPeerAssessments(project);
    }

    private void internalPeerAssessments(Project project) throws Exception {
        peerAssessmentSimulation.internalPeerAssessments(project);
    }

    private void docentAssessments(Project project) throws Exception {
        peerAssessmentSimulation.docentAssessments(project);
    }

    public List<WizardProject> getProjects() {
        List<WizardProject> projects = wizardDao.getProjects();
        return projects.stream().filter(this::notInGroupWorkContext).collect(Collectors.toList());
    }

    private boolean notInGroupWorkContext(WizardProject wizardProject) {
        GroupWorkContext[] values = GroupWorkContext.values();
        for (GroupWorkContext value : values) {
            if (value.toString().equals(wizardProject.getName())) {
                return false;
            }
        }
        return true;
    }

    public void finalizeDossierPhase(Project project) throws Exception {
        phases.endPhase(Phase.DossierFeedback, project, new User(project.getAuthorEmail()));
    }
}


