package uzuzjmd.competence.persistence;

import uzuzjmd.competence.shared.group.PreferenceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dehne on 25.04.2018.
 */
public class SQLSimilarityAlgorithmStorage implements SimilarityAlgorithmStorage {
    @Override
    public void createOrUpdateBatchSimilarityMeasure(
            String competence, Double priority, String... similarityNodeList) {

    }

    @Override
    public void createOrUpdateBatchSimilarityMeasure(
            String competence, Double priority, List<String> similarityNodeList) {

    }

    @Override
    public ArrayList<ArrayList<Object>> getSimilarNodesResultSet(String competence) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<Object>> getSimilaritesForProject(String projectId) {
        return null;
    }

    @Override
    public ArrayList<String> getAllUserForProject(String projectId) {
        return null;
    }

    @Override
    public void addPreferences(
            String projectId, String userId, PreferenceData preferenceData) {

    }
}
