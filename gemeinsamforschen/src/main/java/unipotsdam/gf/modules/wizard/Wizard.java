package unipotsdam.gf.modules.wizard;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.FLTrailConfig;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.compbase.TomcatConceptImporter;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class Wizard {

    private final TomcatConceptImporter concepts;

    @Inject
    WizardDao wizardDao;

    @Inject
    IPhases phases;

    @Inject
    TaskDAO taskDAO;

    @Inject
    ProjectDAO projectDAO;

    @Inject
    ProjectCreationProcess projectCreationProcess;

    @Inject
    GroupFormationProcess groupFormationProcess;

    @Inject
    UserDAO userDAO;

    @Inject
    GroupDAO groupDAO;

    @Inject
    Management management;

    @Inject
    DossierCreationProcess dossierCreationProcess;

    @Inject
    IGroupFinding groupFinding;

    PodamFactoryImpl factory = new PodamFactoryImpl();

    public Wizard() {
        this.concepts = new TomcatConceptImporter();
    }

    /**
     * closes all previous tasks
     *
     * @param taskName
     * @param project
     */
    public void doSpells(TaskName taskName, Project project) throws Exception {

        // get previous tasks including the current
        List<TaskName> previousTasks = getPreviousTasks(taskName);

        // finish previous tasks except the current
        List<TaskName> previousTasksWithoutLast = previousTasks.subList(0, previousTasks.size() - 1);

        for (TaskName previousTaskWL : previousTasksWithoutLast) {
            taskDAO.updateForAll(new Task(previousTaskWL, null, project, Progress.FINISHED));
        }

        // simulate previous phases
        Phase correspondingPhase = phases.getCorrespondingPhase(taskName);
        List<Phase> previousPhases = phases.getPreviousPhases(correspondingPhase);
        for (Phase previousPhase : previousPhases) {
            simulatePhase(project, previousPhase);
        }

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
                case CLOSE_GROUP_FINDING_PHASE: {
                    groupFormationProcess.finalize(project);
                    dossierCreationProcess.start(project);
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
                case FINALIZE_DOSSIER:
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

    public void doSpells(Phase phase, Project project) throws Exception {
        // do previous tasks and phases
        TaskName lastTask = phases.getLastTask(phase);
        doSpells(lastTask, project);
        // simulate the current phase
        simulatePhase(project, phase);
    }

    public void simulatePhase(Project project, Phase previousPhase) throws Exception {
        switch (previousPhase) {
            case GroupFormation:
                createStudents(project);
                groupFormationProcess.finalize(project);
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
    }

    public List<TaskName> getPreviousTasks(TaskName taskName) {
        /*if (taskName.equals(TaskName.WAIT_FOR_PARTICPANTS)) {
            return ;
        }*/
        TaskOrder taskOrder = new TaskOrder();
        List<TaskName> orderedTasks = taskOrder.getOrderedTasks();
        int i = orderedTasks.indexOf(taskName);
        return orderedTasks.subList(0, i + 1);
    }

    public void createStudents(Project project) throws Exception {
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

    public void createDossiers(Project project) {
        //List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {

        }
        // TODO implement
    }

    public void annotateDossiers(Project project) {
        // TODO implement
    }

    public void generateFeedbacks(Project project) {
        // TODO implement
    }

    public void finalizeDossiers(Project project) {
        // TODO implement
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
}


