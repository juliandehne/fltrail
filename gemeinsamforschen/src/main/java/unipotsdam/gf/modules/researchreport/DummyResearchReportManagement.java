package unipotsdam.gf.modules.researchreport;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;

import java.io.File;

public class DummyResearchReportManagement implements ResearchReportManagement {

    /**
     * Utility to creaty dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();

    @Override
    public String createResearchReport(
            ResearchReport researchReport, Project project, User student) {
        return factory.manufacturePojo(ResearchReport.class).getId();
    }

    @Override
    public boolean updateResearchReport(ResearchReport researchReport) {
        return false;
    }

    @Override
    public boolean deleteReport(ResearchReport researchReport) {
        return false;
    }

    @Override
    public File getResearchReport(ResearchReport researchReport) {
        return null;
    }
}
