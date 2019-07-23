package unipotsdam.gf.process.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static unipotsdam.gf.process.tasks.TaskName.ANSWER_REFLECTION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_EXECUTION_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.COLLECT_RESULTS_FOR_ASSESSMENT;
import static unipotsdam.gf.process.tasks.TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.END_LEARNING_GOAL_PERIOD;
import static unipotsdam.gf.process.tasks.TaskName.START_LEARNING_GOAL_PERIOD;
import static unipotsdam.gf.process.tasks.TaskName.UPLOAD_LEARNING_GOAL_RESULT;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_EXECUTION_PHASE_END;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_LEARNING_GOALS;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_LEARNING_GOAL_RESULTS;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_REFLECTION_QUESTIONS_ANSWERS;
import static unipotsdam.gf.process.tasks.TaskName.WORK_ON_LEARNING_GOAL;

public class TaskOrder {
    private List<TaskName> orderedTasks;
    public Comparator<Task> byName = (o1, o2) -> {
        if (o1.getTaskName().equals(o2.getTaskName())) {
            return 0;
        } else {
            return orderedTasks.indexOf(o1.getTaskName()) > orderedTasks.indexOf(o2.getTaskName()) ? -1 : 1;
        }
    };


    public TaskOrder() {
        List<TaskName> result = new ArrayList<>();
        result.add(TaskName.WAIT_FOR_PARTICPANTS);
        result.add(TaskName.WAITING_FOR_GROUP);
        result.add(TaskName.CLOSE_GROUP_FINDING_PHASE);
        // end of group finding phase
        result.add(TaskName.CONTACT_GROUP_MEMBERS);
        result.add(TaskName.WAITING_FOR_STUDENT_DOSSIERS);
        result.add(TaskName.UPLOAD_DOSSIER);
        result.add(TaskName.INTRODUCE_E_PORTFOLIO_STUDENT);
        result.add(TaskName.ANNOTATE_DOSSIER);
        result.add(TaskName.GIVE_FEEDBACK);
        result.add(TaskName.SEE_FEEDBACK);
        result.add(TaskName.REEDIT_DOSSIER);
        result.add(TaskName.INTRODUCE_E_PORTFOLIO_DOCENT);
        result.add(TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE);
        //end of dossier phase
        result.add(WAIT_FOR_LEARNING_GOALS);
        result.add(CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS);
        result.add(START_LEARNING_GOAL_PERIOD);
        result.add(WORK_ON_LEARNING_GOAL);
        result.add(UPLOAD_LEARNING_GOAL_RESULT);
        result.add(ANSWER_REFLECTION_QUESTIONS);
        result.add(WAIT_FOR_LEARNING_GOAL_RESULTS);
        result.add(WAIT_FOR_REFLECTION_QUESTIONS_ANSWERS);
        result.add(END_LEARNING_GOAL_PERIOD);
        result.add(WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION);
        result.add(COLLECT_RESULTS_FOR_ASSESSMENT);
        result.add(WAIT_FOR_EXECUTION_PHASE_END);
        result.add(CLOSE_EXECUTION_PHASE);
        //end of execution
        result.add(TaskName.UPLOAD_PRESENTATION);
        result.add(TaskName.WAIT_FOR_UPLOAD);
        result.add(TaskName.UPLOAD_FINAL_REPORT);
        result.add(TaskName.GIVE_EXTERNAL_ASSESSMENT);
        result.add(TaskName.GIVE_INTERNAL_ASSESSMENT);
        result.add(TaskName.COLLECT_RESULTS_FOR_ASSESSMENT);
        result.add(TaskName.CLOSE_PEER_ASSESSMENTS_PHASE);
        //end of peer-assessment
        result.add(TaskName.WAIT_FOR_GRADING);
        result.add(TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER);
        result.add(TaskName.GIVE_FINAL_GRADES);
        result.add(TaskName.CLOSE_ASSESSMENT_PHASE);
        result.add(TaskName.END_STUDENT);
        result.add(TaskName.END_DOCENT);
        result.add(TaskName.EVALUATION_TECHNISCH);
        result.add(TaskName.EVALUATION_PROZESS);

        this.orderedTasks = result;
    }

    public List<TaskName> getOrderedTasks() {
        return orderedTasks;
    }

    public TaskName getNextTask(TaskName taskName) {
        return orderedTasks.get(orderedTasks.indexOf(taskName) + 1);
    }

    public TaskName getPreviousTask(TaskName taskName) {
        return orderedTasks.get(orderedTasks.indexOf(taskName) - 1);
    }
}
