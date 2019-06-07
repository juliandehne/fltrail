package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.assessment.controller.model.ContributionCategory;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionPart;
import unipotsdam.gf.modules.submission.model.SubmissionPartPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionProjectRepresentation;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
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
    FullSubmission getFullSubmission(String fullSubmissionId, ContributionCategory contributionCategory);

    /**
     * Checks if a full submission id already exists in the database
     *
     * @param fullSubmissionId The id of the full submission
     * @return Returns true if the id exists
     *//*
    boolean existsFullSubmissionId(String fullSubmissionId);*/

    /**
     * Store the submission part text in the database
     *
     * @param submissionPartPostRequest The submission part post request
     * @return The new submission part
     */
    SubmissionPart addSubmissionPart(SubmissionPartPostRequest submissionPartPostRequest);

    /**
     * Get the entire submission part from database
     *
     * @param fullSubmissionId The id of the full submission
     * @param category         The category of the submission
     * @return The returned submission part
     */
    SubmissionPart getSubmissionPart(String fullSubmissionId, Category category);

    /**
     * Get all submission parts based on an id
     *
     * @param fullSubmissionId The id of a full submission
     * @return An ArrayList holding the submission parts
     */
    ArrayList<SubmissionPart> getAllSubmissionParts(String fullSubmissionId);

    /**
     * Get all project representations of submission part for a given project id
     *
     * @param projectName The given project id
     * @return An ArrayList of submission project representations
     */
    ArrayList<SubmissionProjectRepresentation> getSubmissionPartsByProjectId(String projectName);

    /**
     * Checks if a submission part already exists in the database
     *
     * @param fullSubmissionId The id of the full submission
     * @param category         The category of the submission
     * @return Returns true if the submission part exists
     */
    boolean existsSubmissionPart(String fullSubmissionId, Category category);

}
