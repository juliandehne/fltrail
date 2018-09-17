package unipotsdam.gf.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.group.GroupDAO;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.states.PhasesImpl;
import unipotsdam.gf.core.testsandbox.TestList;
import unipotsdam.gf.core.testsandbox.TestListInterface;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessmentDummy;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.modules.groupfinding.DummyGroupfinding;
import unipotsdam.gf.modules.groupfinding.dummy.service.DummyProjectCreationService;
import unipotsdam.gf.modules.journal.DummyJournalImpl;
import unipotsdam.gf.modules.peer2peerfeedback.DummyFeedback;
import unipotsdam.gf.modules.researchreport.DummyResearchReportManagement;
import unipotsdam.gf.modules.researchreport.ResearchReportManagement;

public class GFApplicationBinder extends AbstractBinder {

    /**
     * TODO replace DummyImplementation
     */
    @Override
    protected void configure() {
        bind(CommunicationDummyService.class).to(ICommunication.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(DummyFeedback.class).to(Feedback.class);
        bind(DummyJournalImpl.class).to(IJournal.class);
        bind(PeerAssessmentDummy.class).to(IPeerAssessment.class);
        bind(PhasesImpl.class).to(IPhases.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(DummyResearchReportManagement.class).to(ResearchReportManagement.class);
        bind(TestList.class).to(TestListInterface.class);
        bind(DummyGroupfinding.class).to(IGroupFinding.class);
        bind(DummyProjectCreationService.class).to(DummyProjectCreationService.class);
        bind(UserDAO.class).to(UserDAO.class);
        bind(ProjectDAO.class).to(ProjectDAO.class);
        bind(GroupDAO.class).to(GroupDAO.class);
        bind(MysqlConnect.class).to(MysqlConnect.class);


    }
}
