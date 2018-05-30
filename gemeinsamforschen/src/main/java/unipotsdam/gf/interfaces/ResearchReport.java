package unipotsdam.gf.interfaces;


/**
 * Created by Johannes Zeiße on 30.05.2018.
 */


public interface ResearchReport {

    /**
     * Create a File
     *
     * @param reportId Name of the Report
     * @param userId The id of creator
     */
    File addReport(String reportId, String userId);


    /**
     * Change a File
     *
     * @param reportId Name of the Report
     * @param userId The id of creator
     * @return Returns the Changed File
     */
    File changeReport(String reportId, String userId, String title + "\n" + question + "\n" + goals + "\n" + method + "\n" + research + "\n" + source[] + "\n" + result + "\n" + evaluation);

    /**
     * Delete a File
     *
     * @param reportId Name of the Report
     */
    void deleteReport(String reportId);


    /**
     * Shows the Id of a File
     * @return Returns the Id of a File
     */
    String getreportId();


    /* /**
     * Shows the Path of a File
     *
     * @param reportId Id of the File
     * @return Returns the Path of a FIle
     * /
    String getFilePath(String reportId);
    */

//Add / Change(Override)------------------------------------------------------------------------------------
    /**
     * Add a title to the Report
     *
     * @param reportId Name of the Report
     * @param title title of the Report
     */
    String addTitle(String reportId, String title );

    /**
     * Add a Researchquestion to the Report
     *
     * @param reportId Name of the Report
     * @param question Research question of the Report
     */
    String addResearchQuestion(String reportId, String question);

    /**
     * Add a learning goal to the Report
     *
     * @param reportId Name of the Report
     * @param goals LearningGoals of the Report
     */
    String addLearningGoal(String reportId, String goals );

    /**
     * Add a research method to the Report
     *
     * @param reportId Name of the Report
     * @param method Methods of the Report
     */
    String addMethod(String reportId, String method);

    /**
     * Add a research to the Report
     *
     * @param reportId Name of the Report
     * @param research Research of the Report
     */
    String addResearch(String reportId, String research);

    /**
     * Add a source to the Report
     *
     * @param reportId Name of the Report
     * @param source Sources of the Report
     */
    String addLiteratur(String reportId,String[] source[int i] );  //Kann auch als Text statt Liste gemacht werden (?)

    /**
     * Add a research result to the Report
     *
     * @param reportId Name of the Report
     * @param result research Results of the Report
     */
    String addResearchResult(String reportId, String result);

    /**
     * Add a evaluation to the Report
     *
     * @param reportId Name of the Report
     * @param evaluation Evaluation of the Report
     */
    String addEvaluation(String reportId, String evaluation );

    //Get--------------------------------------------------------

    /**
     * Get a title to the Report
     *
     * @param reportId Name of the Report
     * @returns returns The title of the Report
     */
    String getTitle(String reportId);

    /**
     * Get a Researchquestion to the Report
     *
     * @param reportId Name of the Report
     * @return Returns The Research question of the Report
     */
    String getResearchQuestion(String reportId);

    /**
     * Get a learning goal to the Report
     *
     * @param reportId Name of the Report
     * @return Returns The Learning Goals of the Report
     */
    String getLearningGoal(String reportId);

    /**
     * Get a research method to the Report
     *
     * @param reportId Name of the Report
     * @return Returns The Methods of the Report
     */
    String getMethod(String reportId);

    /**
     * Get a research to the Report
     *
     * @param reportId Name of the Report
     * @return Returns the Research of the Report
     */
    String getResearch(String reportId);

    /**
     * Get a source to the Report
     *
     * @param reportId Name of the Report
     * @return Returns all the Sources of the Report
     */
    String[] getLiteratur(String reportId);

    /**
     * Get a research result to the Report
     *
     * @param reportId Name of the Report
     * @return Returns the research Results of the Report
     */
    String getResearchResult(String reportId);

    /**
     * Get a evaluation to the Report
     *
     * @param reportId Name of the Report
     * @return Returns the Evaluation of the Report
     */
    String getEvaluation(String reportId);
}
