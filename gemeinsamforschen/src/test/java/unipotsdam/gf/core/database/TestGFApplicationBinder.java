package unipotsdam.gf.core.database;

import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.interfaces.*;
import unipotsdam.gf.modules.assessment.controller.service.AssessmentDBCommunication;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.modules.group.DummyGroupfinding;
import unipotsdam.gf.modules.group.DummyProjectCreationService;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupfindingImpl;
import unipotsdam.gf.modules.journal.service.IJournalImpl;
import unipotsdam.gf.modules.peer2peerfeedback.DummyFeedback;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.ManagementImpl;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.researchreport.DummyResearchReportManagement;
import unipotsdam.gf.modules.researchreport.ResearchReportManagement;
import unipotsdam.gf.process.phases.PhasesImpl;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.session.GFContexts;

public class TestGFApplicationBinder extends GFApplicationBinder {

    @Override
    protected void configure() {
        bind(CommunicationDummyService.class).to(ICommunication.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(DummyFeedback.class).to(Feedback.class);
        bind(PeerAssessment.class).to(IPeerAssessment.class);
        bind(PhasesImpl.class).to(IPhases.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(DummyResearchReportManagement.class).to(ResearchReportManagement.class);
        bind(DummyGroupfinding.class).to(IGroupFinding.class);
        bind(DummyProjectCreationService.class).to(DummyProjectCreationService.class);
        bind(UserDAO.class).to(UserDAO.class);
        bind(ProjectDAO.class).to(ProjectDAO.class);
        bind(GroupDAO.class).to(GroupDAO.class);
        bind(GroupfindingImpl.class).to(IGroupFinding.class);
        bind(TaskDAO.class).to(TaskDAO.class);
        bind(IJournalImpl.class).to(IJournal.class);
        bind(AssessmentDBCommunication.class).to(AssessmentDBCommunication.class);
        bind(GFContexts.class).to(GFContexts.class);
        bind(MysqlTestConnect.class).to(MysqlConnect.class);

    }
}
