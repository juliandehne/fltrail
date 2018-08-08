package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;

public interface ISubmission {

    /**
     * Store the full submission text in the database
     *
     * @param request The full submission post request
     * @return The new full submission
     */
    FullSubmission addFullSubmission(FullSubmissionPostRequest request);

    /**
     * Get the entire submission from the databse
     *
     * @param fullSubmissionId The id of the submission
     * @return The full submission
     */
    FullSubmission getFullSubmission(String fullSubmissionId);

    /**
     * Checks if an full submission id already exists in the database
     *
     * @param id The id of the full submission
     * @return Returns true if the id exists
     */
    boolean existsFullSubmissionId(String id);

}
