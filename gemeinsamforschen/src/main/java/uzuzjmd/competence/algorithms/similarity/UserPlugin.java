package uzuzjmd.competence.algorithms.similarity;

/**
 * Created by dehne on 12.09.2017.
 */
public class UserPlugin {

    // TODO implement this with the sqlstorage
/*    public static void createOrUpdateArtificialNode(String competence1, String competence2, Boolean downvote) {

        if (MagicStrings.loggingDBName != null &&  MagicStrings.thesaurusDatabaseUrl != null && MagicStrings
                .thesaurusLogin != null && MagicStrings.thesaurusPassword != null) {
            AnalyticsInitializer.addVoteRequest(competence1, competence2, downvote ? 0 : 1, downvote ? 1 : 0);
        }
        SimilarityAlgorithmStorage queryManager = AlgorithmStorageFactory.boltStorage();
        SimilarityNode similarityNode = new SimilarityNode(competence1 + competence2);
        try {
            similarityNode.persist();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Double priorityWeight = MatchPriority.USER_SIMILARITY_WEIGHT;
        if (downvote) {
            priorityWeight = -priorityWeight;
        }
        queryManager
                .createOrUpdateBatchSimilarityMeasure(new Competence(competence1), priorityWeight, similarityNode);
        queryManager
                .createOrUpdateBatchSimilarityMeasure(new Competence(competence2), priorityWeight, similarityNode);
    }*/
}
