package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.process.progress.ProgressData;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.TaskMapping;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProgessAndTaskMapping {
    private TaskMapping taskMapping;
    private ProgressData progressData;

    public ProgessAndTaskMapping() {
    }

    public ProgessAndTaskMapping(TaskMapping taskMapping, ProgressData progressData) {
        this.taskMapping = taskMapping;
        this.progressData = progressData;
    }

    public TaskMapping getTaskMapping() {
        return taskMapping;
    }

    public void setTaskMapping(TaskMapping taskMapping) {
        this.taskMapping = taskMapping;
    }

    public ProgressData getProgressData() {
        return progressData;
    }

    public void setProgressData(ProgressData progressData) {
        this.progressData = progressData;
    }
}
