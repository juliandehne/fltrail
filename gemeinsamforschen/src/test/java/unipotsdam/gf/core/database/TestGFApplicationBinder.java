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
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.phases.PhasesImpl;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.session.GFContexts;

public class TestGFApplicationBinder extends GFApplicationBinder {



    @Override
    protected void bindMore() {
        bind(MysqlTestConnect.class).to(MysqlConnect.class);
    }
}
