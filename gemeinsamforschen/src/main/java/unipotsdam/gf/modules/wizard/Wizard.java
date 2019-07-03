package unipotsdam.gf.modules.wizard;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInMysqlException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Wizard {

    @Inject
    WizardDao wizardDao;

    @Inject
    IPhases phases;

    @Inject
    TaskDAO taskDAO;

    @Inject
    ProjectCreationProcess projectCreationProcess;

    PodamFactoryImpl factory = new PodamFactoryImpl();

    /**
     * closes all previous tasks
     *
     * @param taskName
     * @param project
     */
    public void doSpells(TaskName taskName, Project project)
            throws UserExistsInRocketChatException, RocketChatDownException, UserExistsInMysqlException, UserDoesNotExistInRocketChatException {
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

    public void doSpells(Phase phase, Project project)
            throws UserExistsInRocketChatException, RocketChatDownException, UserExistsInMysqlException, UserDoesNotExistInRocketChatException {
        // do previous tasks and phases
        TaskName lastTask = phases.getLastTask(phase);
        doSpells(lastTask, project);
        // simulate the current phase
        simulatePhase(project, phase);
    }

    public void simulatePhase(Project project, Phase previousPhase)
            throws UserDoesNotExistInRocketChatException, UserExistsInRocketChatException, UserExistsInMysqlException, RocketChatDownException {
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

    public void createStudents(Project project)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, UserExistsInMysqlException, UserExistsInRocketChatException {
        // TODO implement
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

    private void createMockDataForCompBase(Project project, User student) {
    }

    private void createMockDataForGroupal(Project project, User student) {
        // TODO implement
    }

   /* public void skipGroupfinding(Project project) {

    }*/

    public void createDossiers(Project project) {
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

 /*   public void skipConceptPhase(Project project) {

    }

    public void skipReflexionPhase(Project project) {

    }*/

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

    public void skipPeerAssessment() {
        // TODO implement
    }


    public List<WizardProject> getProjects() {
        List<WizardProject> projects = wizardDao.getProjects();
        List<WizardProject> result = new ArrayList<>();
        // TODO
        return result;
    }
}


