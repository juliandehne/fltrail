package unipotsdam.gf.interfaces;


/**
 * Created by Johannes Zeiße on 30.05.2018.
 */


public interface ResearchReport {

    /**
     * Add a File / Upload or Create
     *
     * @param filename Name of the File
     * @param filePath Path of the File
     * @param userId The id of the uploader of the File
     */
    File addFile(String filename, String filePath, String userId);

    /**
     * Return a File / Download
     *
     * @param filename Name of the File
     * @param filePath Path of the File
     * @return Returns the File to download
     */
    File getFile(String filename, String filePath);

    /**
     * Delete a File
     *
     * @param filename Name of the File
     * @param filePath Path of the File
     */
    void deleteFile(String filename, String filePath);


    /**
     * Shows the Name of a File
     * @return Returns the Name of a File
     */
    String getFileName();


    /**
     * Shows the Path of a File
     *
     * @param filename Name of the File
     * @return Returns the Path of a FIle
     */
    String getFilePath(String filename);


}
