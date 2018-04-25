package uzuzjmd.competence.algorithms.groups.drools;

/**
 * Created by dehne on 08.12.2017.
 */
public class DLearnerPair {
    private String user1Id;
    private String user2Id;
    private Double similarityScore;

    public DLearnerPair() {
    }

    public DLearnerPair(String user1Id, String user2Id, Double similarityScore) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.similarityScore = similarityScore;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public Double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(Double similarityScore) {
        this.similarityScore = similarityScore;
    }
}
