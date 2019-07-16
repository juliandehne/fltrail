package unipotsdam.gf.modules.wizard;

import com.itextpdf.text.DocumentException;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.codehaus.jackson.map.ObjectMapper;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.FLTrailConfig;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPhases;
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
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.compbase.ConceptImporter;
import unipotsdam.gf.modules.wizard.compbase.TomcatConceptImporter;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class Wizard {

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
    UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private Management management;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    SubmissionController submissionController;

    private LoremIpsum loremIpsum;
    private PodamFactoryImpl factory = new PodamFactoryImpl();

    public Wizard() {

        this.concepts = new TomcatConceptImporter();
        this.loremIpsum = new LoremIpsum();
    }

    /**
     * closes all previous tasks
     *
     * @param taskName
     * @param project
     */
    public void doSpells(TaskName taskName, Project project) throws Exception {

        // simulate previous tasks
        Phase correspondingPhase2 = phases.getCorrespondingPhase(taskName);
        simulatePreviousPhases(correspondingPhase2, project);

        // simulate tasks in this phase
        simulateTasksInThisPhase(taskName, project);
    }

    public void simulateTasksInThisPhase(TaskName taskName, Project project) throws Exception {
        Phase correspondingPhase = phases.getCorrespondingPhase(taskName);

        // get previous tasks including the current
        List<TaskName> previousTasks = getPreviousTasks(taskName);
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

    public void doSpells(Phase phase, Project project) throws Exception {
        simulatePreviousPhases(phase, project);
        // simulate the current phase
        simulatePhase(project, phase);
    }

    public void simulatePreviousTasks(Project project, List<TaskName> previousTasks) throws Exception {
        // previous tasks only contains tasks in this phase now
        for (TaskName name : previousTasks) {
            switch (name) {
                case WAIT_FOR_PARTICPANTS: {
                    ProjectStatus participantCount = projectDAO.getParticipantCount(project);
                    if (participantCount.getParticipants() < 6) {
                        createStudents(project);
                    }
                    break;
                }
                case UPLOAD_DOSSIER:
                    createDossiers(project);
                    break;
                case ANNOTATE_DOSSIER:
                    createDossiers(project);
                    annotateDossiers(project);
                    break;
                case GIVE_FEEDBACK: {
                    createDossiers(project);
                    annotateDossiers(project);
                    generateFeedbacks(project);
                    break;
                }
                case REEDIT_DOSSIER:
                    createDossiers(project);
                    annotateDossiers(project);
                    generateFeedbacks(project);
                    finalizeDossiers(project);
                    break;
                case UPLOAD_PRESENTATION:
                    generatePresentationsForAllGroupsAndUploadThem(project);
                    break;
                case UPLOAD_FINAL_REPORT:
                    generatePresentationsForAllGroupsAndUploadThem(project);
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

    public void simulatePhase(Project project, Phase phase) throws Exception {
        // phase will be ended as a call to phases, at the end in any case
        switch (phase) {
            case GroupFormation:
                createStudents(project);
                break;
            case DossierFeedback: {
                createDossiers(project);
                annotateDossiers(project);
                generateFeedbacks(project);
                finalizeDossiers(project);
                break;
            }
            case Execution: {

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


    public void simulatePreviousPhases(Phase correspondingPhase, Project project) throws Exception {
        List<Phase> previousPhases = phases.getPreviousPhases(correspondingPhase);
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

    public void createStudents(Project project) throws Exception {
        if (projectDAO.getParticipantCount(project).getParticipants()  == 0) {
            ArrayList<User> students = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 30; i++) {
                try {
                    User user = factory.manufacturePojo(User.class);
                    user.setStudent(true);

                    user.setRocketChatUsername("studentwizard" + random.nextInt(1000000));
                    user.setEmail("studentwizard" + random.nextInt(1000000) + "@stuff.com");
                    user.setPassword("egal");
                    projectCreationProcess.deleteUser(user);
                    projectCreationProcess.createUser(user);
                    projectCreationProcess.studentEntersProject(project, user);
                    students.add(user);
                } catch (Exception e) {
                    System.out.println(e);
                    // might have been a problem with UUID generation should not crash
                }
            }
            if (FLTrailConfig.wizardSimulatesFullAlgorithms) {
                for (User student : students) {
                    GroupFormationMechanism groupMechanismSelected = management.getProjectConfiguration(project).getGroupMechanismSelected();
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
    }

    /**
     * @param project
     * @param student
     */
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
        ArrayList<String> result = new ArrayList<>();
        List<String> tags = projectDAO.getTags(project);

        if (tags == null || tags.isEmpty()) {
            tags = concepts.getNumberedConcepts(5);
            projectDAO.persistTagsForWizard(project, tags);
        }
        result.addAll(tags);
        return result;
    }

    /**
     * @param project
     * @param user
     */
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

    public void createDossiers(Project project) throws IOException, DocumentException {
        if (submissionController.getAllGroupsWithDossierUploaded(project).size() == 0) {
            List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
            for (Group group : groupsByProjectName) {
                // add first submission
                User representativUser = groupDAO.getRepresentativUser(group, project);
                // we have to persist it in quill js style
                String text = loremIpsum.getWords(500);
                text = convertTextToQuillJs(text);

                String title = concepts.getNumberedConcepts(3).stream().reduce((x, y) -> x + " " + y).get();
                FullSubmissionPostRequest submission =
                        new FullSubmissionPostRequest(group, text, FileRole.DOSSIER, project, Visibility.PUBLIC,
                                title);
                // TODO @Axel ich verstehe noch nicht, warum FullSubmissionPostRequest sowohl ein Text als auch ein HMTL
                // Feld hat
                submission.setText(text);
                FullSubmission fullSubmission =
                        dossierCreationProcess.addDossier(submission, representativUser, project);
                submission.setId(fullSubmission.getId());
            }
        }
        // TODO implement
    }

    public void annotateDossiers(Project project) {
        // TODO implement
    }

    public void generateFeedbacks(Project project) {
        if (submissionController.getAllGroupsWithFinalizedFeedback(project).size() == 0) {

        }
        // TODO implement
    }

    public void finalizeDossiers(Project project) throws IOException, DocumentException {
        if (submissionController.getAllGroupsWithFinalizedDossier(project).size() == 0) {
            List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
            for (Group group : groupsByProjectName) {
                User representativUser = groupDAO.getRepresentativUser(group, project);
                FullSubmission fullSubmissionBy =
                        submissionController.getFullSubmissionBy(group.getId(), project, FileRole.DOSSIER, 0);
                FullSubmissionPostRequest fullSubmissionPostRequest =
                        new FullSubmissionPostRequest(group, fullSubmissionBy.getText(), fullSubmissionBy.getFileRole(),
                                project, fullSubmissionBy.getVisibility(), fullSubmissionBy.getHeader());
                dossierCreationProcess.updateSubmission(fullSubmissionPostRequest, representativUser, project, true);
            }
        }
    }

    public void generatePresentationsForAllGroupsAndUploadThem(Project project) {
        // TODO implement
    }

    public void generateFinalReportsForAllGroupsAndUploadThem(Project project) {
        // TODO implement
    }

    public void externalPeerAssessments(Project project) {
        // TODO implement
    }

    public void internalPeerAssessments(Project project) {
        // TODO implement
    }

    public void docentAssessments(Project project) {
        // TODO implement
    }

    public List<WizardProject> getProjects() {
        List<WizardProject> projects = wizardDao.getProjects();
        List<WizardProject> collected = projects.stream().filter(wizardProject -> notInGroupWorkContext(wizardProject))
                .collect(Collectors.toList());
        return collected;
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

    public static String convertTextToQuillJs(String text) throws IOException {
        HashMap<String, String> quillJsContents = new HashMap<>();
        quillJsContents.put("insert", text);
        ArrayList<HashMap<String, String>> lArray = new ArrayList<>();
        lArray.add(quillJsContents);
        lArray.add(quillJsContents);
        HashMap<String, ArrayList<HashMap<String,String>>> lObject = new HashMap<>();
        lObject.put("ops", lArray);
        ObjectMapper mapper = new ObjectMapper();
        text = mapper.writeValueAsString(lObject);
        return text;
    }
}


