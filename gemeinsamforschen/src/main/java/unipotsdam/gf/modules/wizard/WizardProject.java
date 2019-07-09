package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskName;

public class WizardProject extends Project {

    private TaskName taskName;
    private Phase phase;

    public WizardProject(String projectName, String taskName, String phase) {
        super(projectName);
        if (taskName != null) {
            this.taskName = TaskName.valueOf(taskName);
        }
        if (phase != null) {
            this.phase = Phase.valueOf(phase);
        }
    }
}
