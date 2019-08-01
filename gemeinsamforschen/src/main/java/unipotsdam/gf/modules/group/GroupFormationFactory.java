package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.preferences.groupal.PGroupAlMatcher;
import unipotsdam.gf.modules.group.random.RandomGroupAlgorithm;

import javax.inject.Inject;

public class GroupFormationFactory {

    @Inject
    RandomGroupAlgorithm randomGroupAlgorithm;

    @Inject
    PGroupAlMatcher pGroupAlMatcher;

    @Inject
    CompBaseMatcher compBaseMatcher;

    @Inject
    SingleGroupMatcher singleGroupMatcher;

    public GroupFormationAlgorithm instance() {
        return randomGroupAlgorithm;
    }

    public GroupFormationAlgorithm instance(GroupFormationMechanism gfm) {
        switch (gfm) {
            case Manual: {
                return randomGroupAlgorithm;
            }
            case LearningGoalStrategy: {
                return compBaseMatcher;
            }
            case UserProfilStrategy: {
                return pGroupAlMatcher;
            }
            case SingleUser: {
                return singleGroupMatcher;
                //return bigGroupMatcher;
            }
        }
        return instance();
    }
}
