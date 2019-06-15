package unipotsdam.gf.process.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskOrder {
    private List<TaskName> orderedTasks;

    public TaskOrder(){
        List<TaskName> result = new ArrayList<>();
        result.add(TaskName.WAIT_FOR_PARTICPANTS);
        result.add(TaskName.WAITING_FOR_GROUP);
        result.add(TaskName.CLOSE_GROUP_FINDING_PHASE);
        result.add(TaskName.CONTACT_GROUP_MEMBERS);
        result.add(TaskName.WAITING_FOR_STUDENT_DOSSIERS);
        result.add(TaskName.UPLOAD_DOSSIER);
        result.add(TaskName.OPTIONAL_PORTFOLIO_ENTRY);
        result.add(TaskName.ANNOTATE_DOSSIER);
        result.add(TaskName.GIVE_FEEDBACK);
        result.add(TaskName.SEE_FEEDBACK);
        result.add(TaskName.REEDIT_DOSSIER);
        result.add(TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE);
        result.add(TaskName.REFLECT_DOSSIER_CREATION);
        //end of execution phase
        result.add(TaskName.ANSWER_REFLEXION_QUESTIONS);
        result.add(TaskName.WAIT_FOR_REFLECTION);
        result.add(TaskName.CLOSE_EXECUTION_PHASE);
        result.add(TaskName.UPLOAD_PRESENTATION);
        result.add(TaskName.UPLOAD_FINAL_REPORT);
        result.add(TaskName.GIVE_EXTERNAL_ASSESSMENT);
        result.add(TaskName.COLLECT_RESULTS_FOR_ASSESSMENT);
        result.add(TaskName.WAIT_FOR_PEER_ASSESSMENTS);
        result.add(TaskName.GIVE_ASSESSMENT);
        result.add(TaskName.SEE_ASSESSMENT);
        result.add(TaskName.FINALIZE_ASSESSMENT);
        result.add(TaskName.CLOSE_ASSESSMENT_PHASE);
        result.add(TaskName.END);
        this.orderedTasks = result;
    }
    public TaskName getNextTask(TaskName taskName){
        return orderedTasks.get(orderedTasks.indexOf(taskName)+1);
    }

    public TaskName getPreviousTask(TaskName taskName){
        return orderedTasks.get(orderedTasks.indexOf(taskName)-1);
    }

    public Comparator<Task> byName = (o1, o2) -> {
        if (o1.getTaskName().equals(o2.getTaskName())) {
            return 0;
        } else {
            return orderedTasks.indexOf(o1.getTaskName()) > orderedTasks.indexOf(o2.getTaskName()) ? -1 : 1;
        }
    };
}
