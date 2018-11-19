package unipotsdam.gf.process.tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        result.add(TaskName.ANNOTATE_DOSSIER);
        result.add(TaskName.GIVE_FEEDBACK);
        result.add(TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE);
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
