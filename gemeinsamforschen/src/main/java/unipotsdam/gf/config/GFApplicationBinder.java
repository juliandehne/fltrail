package unipotsdam.gf.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import unipotsdam.gf.interfaces.*;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.assessment.controller.service.AssessmentDBCommunication;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.modules.feedback.FeedbackImpl;
import unipotsdam.gf.modules.group.DummyProjectCreationService;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupfindingImpl;
import unipotsdam.gf.modules.journal.service.IJournalImpl;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.ManagementImpl;
import unipotsdam.gf.modules.project.ProjectConfigurationDAO;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.researchreport.DummyResearchReportManagement;
import unipotsdam.gf.modules.researchreport.ResearchReportManagement;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.MysqlConnectImpl;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.PhasesImpl;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.session.GFContext;
import unipotsdam.gf.session.GFContexts;

public class GFApplicationBinder extends AbstractBinder {


    @Override
    protected void configure() {
        bind(CommunicationDummyService.class).to(ICommunication.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(PeerAssessment.class).to(IPeerAssessment.class);
        bind(PhasesImpl.class).to(IPhases.class);
        bind(GFContext.class).to(GFContext.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(DummyResearchReportManagement.class).to(ResearchReportManagement.class);
        bind(IJournalImpl.class).to(IJournal.class);
        bind(GroupfindingImpl.class).to(IGroupFinding.class);
        bind(AssessmentDBCommunication.class).to(AssessmentDBCommunication.class);
        bind(GFContexts.class).to(GFContexts.class);
        bind(ProjectCreationProcess.class).to(ProjectCreationProcess.class);
        bind(GroupFormationProcess.class).to(GroupFormationProcess.class);
        bind(ConstraintsImpl.class).to(ConstraintsImpl.class);
        bind(DossierCreationProcess.class).to(DossierCreationProcess.class);
        bind(SubmissionController.class).to(SubmissionController.class);
        bind(AnnotationController.class).to(AnnotationController.class);
        bind(ProjectConfigurationDAO.class).to(ProjectConfigurationDAO.class);
        bind(DummyProjectCreationService.class).to(DummyProjectCreationService.class);
        bind(UserDAO.class).to(UserDAO.class);
        bind(ProjectDAO.class).to(ProjectDAO.class);
        bind(GroupDAO.class).to(GroupDAO.class);
        bind(TaskDAO.class).to(TaskDAO.class);
        bind(FeedbackImpl.class).to(Feedback.class);

        bindMore();
    }

    protected void bindMore() {
        bind(MysqlConnectImpl.class).to(MysqlConnect.class);
    }
}
