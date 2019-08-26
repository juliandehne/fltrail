package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WizardDao {


    @Inject
    private SubmissionController submissionController;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    ProjectDAO projectDAO;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private MysqlConnect connect;

    /**
     * SELECT p.name, p.phase, t.taskName FROM projects
     * p join tasks t on p.name = t.projectName
     * where t.progress = "JUSTSTARTED"
     * Group By p.phase ORDER by t.created ASC
     *
     * @return gives a list with all projects and their latest unfinished task
     */
    public List<WizardProject> getProjects() {
        connect.connect();
        String mysqlRequest =
                " SELECT p.name, p.phase, t.taskName FROM projects "
                        + " p join tasks t on p.name = t.projectName"
                        + " where NOT t.progress = ? "
                        + " Group By p.phase, p.name ORDER by t.created ASC ";
        List<WizardProject> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, Progress.FINISHED);
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("name");
            String taskName = vereinfachtesResultSet.getString("taskName");
            String phase = vereinfachtesResultSet.getString("phase");
            result.add(new WizardProject(project, taskName, phase));
        }
        connect.close();
        return result;
    }

    Set<TaskName> getWizardrelevantTaskStatus(Project project) {
        //relevantTasks.add(TaskName.WAITING_FOR_GROUP);
        Set<TaskName> relevantTaskList = getRelevantTaskSet();
        String query =
                "SELECT t.taskName from tasks t" +
                        " WHERE t.progress= ? AND t.projectName = ? " +
                        "AND t.taskName NOT IN (SELECT t2.taskName from tasks t2 WHERE t2.progress<>? " +
                        "AND t2.projectName=?) group by t.taskName";

        Set<TaskName> result = new HashSet<>();
        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, Progress.FINISHED.name(), project.getName(),
                        Progress.FINISHED.name(), project.getName());
        while (vereinfachtesResultSet.next()) {
            TaskName taskName = TaskName.valueOf(vereinfachtesResultSet.getString("taskName"));
            if (relevantTaskList.contains(taskName)) {
                result.add(taskName);
            }
        }
        connect.close();

        /*
         * we are currently rarely using INPROGRESS as a state, that is the reson for the workaround
         */
        if (projectDAO.getParticipantCount(project).getParticipants() > 5) {
            result.add(TaskName.WAIT_FOR_PARTICPANTS);
        }

        correctDossierStatus(project, result);
        correctAnnotationStatus(project, result);
        correctCreatePortfolioStatus(project, result);
        //correctPortfolioFeedback(project, result);

        if (reflectiveQuestionsAreAnswered(project)) {
            result.add(TaskName.ANSWER_REFLECTION_QUESTIONS);
        }

        return result;
    }

    public Boolean reflectiveQuestionsAreAnswered(Project project) {
        Boolean result = false;
        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(
                "SELECT * from tasks where taskName = ? and projectName = ? and progress = ?",
                TaskName.ANSWER_REFLECTION_QUESTIONS, project.getName(), Progress.FINISHED.name());
        if (vereinfachtesResultSet != null) {
            result = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

   /* private void correctPortfolioFeedback(Project project, Set<TaskName> result) {
        if (!ReflectionPhaseSimulation.FEEDBACK_IMPLEMENTED) {
            result.add(TaskName.DOCENT_GIVE_PORTOLIO_FEEDBACK);
        }
    }*/

    private void correctCreatePortfolioStatus(Project project, Set<TaskName> result) {
        List<FullSubmission> projectSubmissions =
                submissionController.getProjectSubmissions(project, FileRole.PORTFOLIO_ENTRY, Visibility.GROUP);
        if (projectSubmissions != null && !projectSubmissions.isEmpty() && projectSubmissions.size() > 3) {
            result.add(TaskName.WIZARD_CREATE_PORTFOLIO);
        }
    }

    private void correctDossierStatus(Project project, Set<TaskName> relevantTaskList) {
        List<Group> allGroupsWithDossierUploaded = submissionController.getAllGroupsWithDossierUploaded(project);
        HashSet<Integer> allUpLoadedRemovedDuplicates = allGroupsWithDossierUploaded.stream().map(Group::getId)
                .collect(Collectors.toCollection(HashSet::new));
        int existingGroupSize = groupDAO.getGroupsByProjectName(project.getName()).size();
        if (allUpLoadedRemovedDuplicates.size() == existingGroupSize && existingGroupSize > 0) {
            relevantTaskList.add(TaskName.UPLOAD_DOSSIER);
        }
    }

    private void correctAnnotationStatus(Project project, Set<TaskName> relevantTaskList) {
        boolean allAnnotated = true;
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            Task annotateDossierTask = taskDAO.getTasksWithTaskName(group.getId(), project, TaskName.ANNOTATE_DOSSIER);
            if (annotateDossierTask == null || annotateDossierTask.getProgress() != Progress.FINISHED) {
                allAnnotated = false;
            }
        }
        if (!allAnnotated) {
            relevantTaskList.remove(TaskName.ANNOTATE_DOSSIER);
        }
    }

    HashMap<TaskName, Progress> getWizardRelevantTaskMap(Project project) {
        HashMap<TaskName, Progress> result = new HashMap<>();
        //relevantTasks.add(TaskName.WAITING_FOR_GROUP);
        Set<TaskName> relevantTaskList = getRelevantTaskSet();

        String query = "SELECT * from tasks t" + " where and t.projectName = ? GROUP by (t.taskName)";

        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        while (vereinfachtesResultSet.next()) {
            TaskName taskName = TaskName.valueOf(vereinfachtesResultSet.getString("taskName"));
            Progress progress = Progress.valueOf(vereinfachtesResultSet.getString("t.progress"));
            if (relevantTaskList.contains(taskName))
                result.put(taskName, progress);
        }
        connect.close();

        return result;
    }

    private Set<TaskName> getRelevantTaskSet() {
        Set<TaskName> relevantTasks = new HashSet<>();
        relevantTasks.add(TaskName.WAIT_FOR_PARTICPANTS);
        relevantTasks.add(TaskName.UPLOAD_DOSSIER);
        relevantTasks.add(TaskName.ANNOTATE_DOSSIER);
        relevantTasks.add(TaskName.GIVE_FEEDBACK);
        relevantTasks.add(TaskName.REEDIT_DOSSIER);

        // execution phase
        relevantTasks.add(TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS);
        relevantTasks.add(TaskName.WIZARD_CREATE_PORTFOLIO);
        relevantTasks.add(TaskName.DOCENT_GIVE_PORTOLIO_FEEDBACK);
        relevantTasks.add(TaskName.CHOOSE_PORTFOLIO_ENTRIES);
        relevantTasks.add(TaskName.ANSWER_REFLECTION_QUESTIONS);
        // assessment phase
        relevantTasks.add(TaskName.UPLOAD_PRESENTATION);
        relevantTasks.add(TaskName.UPLOAD_FINAL_REPORT);
        relevantTasks.add(TaskName.GIVE_EXTERNAL_ASSESSMENT);
        relevantTasks.add(TaskName.GIVE_INTERNAL_ASSESSMENT);
        relevantTasks.add(TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER);

        /*
        List<String> stringList = relevantTasks.stream().map(Enum::name).collect(Collectors.toList());

        MysqlUtil mysqlUtil = new MysqlUtil();
        return mysqlUtil.createConcatenatedString(stringList);
        */
        return relevantTasks;
    }
}
