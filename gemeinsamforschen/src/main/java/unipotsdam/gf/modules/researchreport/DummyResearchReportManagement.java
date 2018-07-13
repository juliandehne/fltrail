package unipotsdam.gf.modules.researchreport;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.core.management.user.User;

import java.io.File;

public class DummyResearchReportManagement implements ResearchReportManagement {
    @Override
    public String createResearchReport(ResearchReport researchReport, User student) {
        throw new NotImplementedException();
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
