package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DossierCreationProcess {

    @Inject
    private Management management;
    
    @Inject
    private UserDAO userDAO;

    @Inject
    private TaskDAO taskDAO;

    public void startDossierPhase(Project project) {
        taskDAO.persistMemberTask(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }
}
