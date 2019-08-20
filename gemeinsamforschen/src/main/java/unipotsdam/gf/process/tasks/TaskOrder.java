package unipotsdam.gf.process.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static unipotsdam.gf.process.tasks.TaskName.ANNOTATE_DOSSIER;
import static unipotsdam.gf.process.tasks.TaskName.ANSWER_REFLECTION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.CHOOSE_PORTFOLIO_ENTRIES;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_ASSESSMENT_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_EXECUTION_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_GROUP_FINDING_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_PEER_ASSESSMENTS_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.COLLECT_RESULTS_FOR_ASSESSMENT;
import static unipotsdam.gf.process.tasks.TaskName.CONTACT_GROUP_MEMBERS;
import static unipotsdam.gf.process.tasks.TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.DOCENT_GIVE_PORTOLIO_FEEDBACK;
import static unipotsdam.gf.process.tasks.TaskName.END_DOCENT;
import static unipotsdam.gf.process.tasks.TaskName.END_STUDENT;
import static unipotsdam.gf.process.tasks.TaskName.EVALUATION_PROZESS;
import static unipotsdam.gf.process.tasks.TaskName.EVALUATION_TECHNISCH;
import static unipotsdam.gf.process.tasks.TaskName.FEEDBACK_REFLECTION_QUESTION_ANSWER;
import static unipotsdam.gf.process.tasks.TaskName.GIVE_EXTERNAL_ASSESSMENT;
import static unipotsdam.gf.process.tasks.TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER;
import static unipotsdam.gf.process.tasks.TaskName.GIVE_FEEDBACK;
import static unipotsdam.gf.process.tasks.TaskName.GIVE_FINAL_GRADES;
import static unipotsdam.gf.process.tasks.TaskName.GIVE_INTERNAL_ASSESSMENT;
import static unipotsdam.gf.process.tasks.TaskName.INTRODUCE_E_PORTFOLIO_DOCENT;
import static unipotsdam.gf.process.tasks.TaskName.INTRODUCE_E_PORTFOLIO_STUDENT;
import static unipotsdam.gf.process.tasks.TaskName.LOOK_AT_REFLECTION_QUESTION_FEEDBACK;
import static unipotsdam.gf.process.tasks.TaskName.REEDIT_DOSSIER;
import static unipotsdam.gf.process.tasks.TaskName.SEE_LEARNING_GOAL_SELECTION_AGAIN;
import static unipotsdam.gf.process.tasks.TaskName.UPLOAD_DOSSIER;
import static unipotsdam.gf.process.tasks.TaskName.UPLOAD_FINAL_REPORT;
import static unipotsdam.gf.process.tasks.TaskName.UPLOAD_PRESENTATION;
import static unipotsdam.gf.process.tasks.TaskName.WAITING_FOR_GROUP;
import static unipotsdam.gf.process.tasks.TaskName.WAITING_FOR_STUDENT_DOSSIERS;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_EXECUTION_PHASE_END;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_GRADING;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_PARTICPANTS;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_REFLECTION_QUESTION_CHOICE;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_UPLOAD;
import static unipotsdam.gf.process.tasks.TaskName.WIZARD_CREATE_PORTFOLIO;

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
        result.add(WAIT_FOR_PARTICPANTS);
        result.add(WAITING_FOR_GROUP);
        result.add(CLOSE_GROUP_FINDING_PHASE);
        // end of group finding phase
        result.add(CONTACT_GROUP_MEMBERS);
        result.add(WAITING_FOR_STUDENT_DOSSIERS);
        result.add(UPLOAD_DOSSIER);
        result.add(ANNOTATE_DOSSIER);
        result.add(GIVE_FEEDBACK);
        result.add(REEDIT_DOSSIER);
        result.add(CLOSE_DOSSIER_FEEDBACK_PHASE);
        //end of dossier phase
        result.add(INTRODUCE_E_PORTFOLIO_STUDENT);
        result.add(WAIT_FOR_REFLECTION_QUESTION_CHOICE);
        result.add(INTRODUCE_E_PORTFOLIO_DOCENT);
        result.add(CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS);
        result.add(FEEDBACK_REFLECTION_QUESTION_ANSWER);
        result.add(LOOK_AT_REFLECTION_QUESTION_FEEDBACK);
        result.add(WIZARD_CREATE_PORTFOLIO);
        result.add(SEE_LEARNING_GOAL_SELECTION_AGAIN);
        result.add(DOCENT_GIVE_PORTOLIO_FEEDBACK);
        result.add(ANSWER_REFLECTION_QUESTIONS);
        result.add(CHOOSE_PORTFOLIO_ENTRIES);
        result.add(COLLECT_RESULTS_FOR_ASSESSMENT);
        result.add(WAIT_FOR_EXECUTION_PHASE_END);
        result.add(CLOSE_EXECUTION_PHASE);
        //end of execution
        result.add(UPLOAD_PRESENTATION);
        result.add(WAIT_FOR_UPLOAD);
        result.add(UPLOAD_FINAL_REPORT);
        result.add(GIVE_EXTERNAL_ASSESSMENT);
        result.add(GIVE_INTERNAL_ASSESSMENT);
        result.add(COLLECT_RESULTS_FOR_ASSESSMENT);
        result.add(CLOSE_PEER_ASSESSMENTS_PHASE);
        //end of peer-assessment
        result.add(WAIT_FOR_GRADING);
        result.add(GIVE_EXTERNAL_ASSESSMENT_TEACHER);
        result.add(GIVE_FINAL_GRADES);
        result.add(CLOSE_ASSESSMENT_PHASE);
        result.add(END_STUDENT);
        result.add(END_DOCENT);
        result.add(EVALUATION_TECHNISCH);
        result.add(EVALUATION_PROZESS);

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
