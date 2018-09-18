package unipotsdam.gf.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.states.PhasesImpl;
import unipotsdam.gf.core.testsandbox.TestList;
import unipotsdam.gf.core.testsandbox.TestListInterface;
import unipotsdam.gf.interfaces.*;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.modules.groupfinding.DummyGroupfinding;
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
        bind(PeerAssessment.class).to(IPeerAssessment.class);
        bind(PhasesImpl.class).to(IPhases.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(DummyResearchReportManagement.class).to(ResearchReportManagement.class);
        bind(TestList.class).to(TestListInterface.class);
        bind(DummyGroupfinding.class).to(IGroupFinding.class);

    }
}
