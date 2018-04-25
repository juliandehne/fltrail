package uzuzjmd.competence.persistence;


import uzuzjmd.competence.shared.group.PreferenceData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dehne on 29.09.2017.
 */
public interface SimilarityAlgorithmStorage {
    void createOrUpdateBatchSimilarityMeasure(
           String competence, Double priority, String... similarityNodeList);

    void createOrUpdateBatchSimilarityMeasure(
           String competence, Double priority, List<String> similarityNodeList);

    /**
     * n.id,r2.weight,s.id,r1.weight,p.id
     * @param competence
     * @return
     */
    ArrayList<ArrayList<Object>> getSimilarNodesResultSet(String competence);

    ArrayList<ArrayList<Object>> getSimilaritesForProject(String projectId);

    ArrayList<String> getAllUserForProject(String projectId);

    void addPreferences(String projectId, String userId, PreferenceData preferenceData);
}
