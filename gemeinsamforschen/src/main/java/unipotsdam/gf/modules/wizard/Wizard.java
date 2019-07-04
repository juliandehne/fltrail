package unipotsdam.gf.modules.wizard;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInMysqlException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.compbase.ConceptImporter;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import java.util.*;

public class Wizard {

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

    PodamFactoryImpl factory = new PodamFactoryImpl();

    /**
     * closes all previous tasks
     *
     * @param taskName
     * @param project
     */
    public void doSpells(TaskName taskName, Project project) throws Exception {
        // finish previous tasks
        List<TaskName> previousTasks = getPreviousTasks(taskName);
        if (previousTasks != null) {
            for (TaskName previousTask : previousTasks) {
                taskDAO.updateForAll(new Task(previousTask, null, project, Progress.FINISHED));
            }
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

        for (TaskName name : previousTasksNotInThisPhase) {
            switch (name) {
                case WAIT_FOR_PARTICPANTS: {
                    createStudents(project);
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
            case Assessment:{
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
        if (taskName.equals(TaskName.WAIT_FOR_PARTICPANTS)) {
            return null;
        }
        TaskOrder taskOrder = new TaskOrder();
        List<TaskName> orderedTasks = taskOrder.getOrderedTasks();
        return orderedTasks.subList(0, orderedTasks.indexOf(taskName) - 1);
    }

    public void createStudents(Project project) throws Exception {
        ArrayList<User> students = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            try {
                User user = factory.manufacturePojo(User.class);
                user.setStudent(true);
                String uuid = UUID.randomUUID().toString();
                user.setRocketChatUsername("student" + uuid.hashCode());
                user.setEmail("student" + uuid.hashCode() + "@stuff.com");
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
        for (User student : students) {
            projectCreationProcess.studentEntersProject(project, student);
            // mock groupal data generation in case manual group formation is tested
            createMockDataForGroupal(project, student);
            // mock compbase data is generated
            createMockDataForCompBase(project, student);
        }
    }

    /**
     *
     * @param project
     * @param student
     */
    private void createMockDataForCompBase(Project project, User student) throws Exception {
        PreferenceData preferenceData = factory.manufacturePojo(PreferenceData.class);
        // check if tags were persisted for this project
        ArrayList<String> tags = getOrPersistTags(project);
        // if not persist new ones
        preferenceData.setTagsSelected(tags);
        String prefix = "Ich interessiere mich für ";
        List<String> concepts = ConceptImporter.getNumberedConcepts(5);
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
            tags = ConceptImporter.getNumberedConcepts(5);
            projectDAO.persistTagsForWizard(project, tags);
        }
        return result;
    }

    /**
     *
     * @param project
     * @param user
     */
    private void createMockDataForGroupal(Project project, User user) {
        HashMap<String, String> data = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 20 ; i++) {
            int questionId = random.nextInt(27);
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
        return projects;
    }
}


