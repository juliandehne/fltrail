package uzuzjmd.competence.algorithms.groups.optaplanner;

import uzuzjmd.competence.algorithms.groups.drools.DLearner;
import uzuzjmd.competence.algorithms.groups.drools.DLearnerPair;

import java.util.List;

/**
 * Created by dehne on 08.12.2017.
 */
public class SimilarityDBResult {

    private final List<DLearner> users;
    private List<DLearnerPair> userPairs;

    private Integer numberOf3Groups;
    private Integer numberOf4Groups;

    public SimilarityDBResult(
            List<DLearnerPair> userPairs, List<DLearner>users, Integer numberOf3Groups, Integer numberOf4Groups, String
            projectId) {
        this.userPairs = userPairs;
        this.users = users;
        this.numberOf3Groups = numberOf3Groups;
        this.numberOf4Groups = numberOf4Groups;
        this.projectId = projectId;
    }

    public List<DLearner> getUsers() {
        return users;
    }


    public Integer getNumberOf3Groups() {
        return numberOf3Groups;
    }

    public void setNumberOf3Groups(Integer numberOf3Groups) {
        this.numberOf3Groups = numberOf3Groups;
    }

    public Integer getNumberOf4Groups() {
        return numberOf4Groups;
    }

    public void setNumberOf4Groups(Integer numberOf4Groups) {
        this.numberOf4Groups = numberOf4Groups;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    private String projectId;


    public List<DLearnerPair> getUserPairs() {
        return userPairs;
    }

    public void setUserPairs(List<DLearnerPair> userPairs) {
        this.userPairs = userPairs;
    }



}
