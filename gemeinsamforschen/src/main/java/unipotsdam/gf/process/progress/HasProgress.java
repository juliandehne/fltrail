package unipotsdam.gf.process.progress;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

public interface HasProgress {
    ProgressData getProgressData(Project project);
}
