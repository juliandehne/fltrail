package unipotsdam.gf.process.tasks.progress;

import unipotsdam.gf.process.tasks.Task;

public abstract class TaskProgress {

    private Task task;

    public TaskProgress(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
