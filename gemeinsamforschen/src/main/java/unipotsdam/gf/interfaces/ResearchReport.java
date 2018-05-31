package unipotsdam.gf.interfaces;
import java.io.*;

/**
 * Created by Johannes Zeiße on 30.05.2018.
 */


public interface ResearchReport {

    /**
     * Create a File
     *
     * @param researchReport Name of the Report
     * @return Returns the reportId
     */
    String createReseachReport(ResearchReport researchReport);


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
