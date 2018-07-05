package unipotsdam.gf.modules.researchreport;

import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;

/**
 * Created by Johannes Zeiße on 30.05.2018.
 */


public interface ResearchReportManagement {

    /**
     * Create a File
     *
     * @param researchReport Name of the Report
     * @return Returns the reportId
     */
    String createResearchReport(ResearchReport researchReport);


    /**
     * Change a File
     *
     * @param researchReport Name of the Report
     * @return Returns if the report is updated
     */
    boolean updateResearchReport(ResearchReport researchReport);

    /**
     * Delete a File
     *
     * @param researchReport Name of the Report
     */
    boolean deleteReport(ResearchReport researchReport);


    /**
     * Shows the Id of a File
     * @param researchReport Name of the Report
     * @return Returns the Report
     */
    File getResearchReport(ResearchReport researchReport);



}
