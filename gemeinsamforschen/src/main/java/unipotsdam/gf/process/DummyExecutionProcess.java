package unipotsdam.gf.process;

import com.itextpdf.text.DocumentException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class DummyExecutionProcess implements IExecutionProcess {

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    public void start(Project project) {
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_EXECUTION_PHASE, Phase.Execution);
    }

    @Override
    public LearningGoalRequestResult saveLearningGoalsAndReflectionQuestions(LearningGoalRequest learningGoalRequest) {
        return null;
    }

    @Override
    public void endSavingLearningGoalsAndReflectionQuestions(Project project) {

    }

    @Override
    public void answerReflectionQuestion(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) {

    }

    @Override
    public void selectPortfolioEntries(Project project, User user, List<FullSubmission> selectedPortfolioEntries) throws Exception {

    }

    @Override
    public void saveGroupSubmission(Project project, int groupId, String html) throws IOException, DocumentException {

    }

    @Override
    public boolean isPhaseCompleted(Project project) {
        return true;
    }

    @Override
    public void finishPhase(Project project) throws Exception {
        User user = userDAO.getUserByEmail(project.getAuthorEmail());
        Task task = new Task(TaskName.CLOSE_EXECUTION_PHASE, user, project, Progress.FINISHED);
        taskDAO.updateForUser(task);
    }
}
