package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.preferences.groupal.GroupAlMatcher;
import unipotsdam.gf.modules.group.random.RandomGroupAlgorithm;

import javax.inject.Inject;

public class GroupFormationFactory {

    @Inject
    RandomGroupAlgorithm randomGroupAlgorithm;

    @Inject
    GroupAlMatcher groupAlMatcher;

    @Inject
    CompBaseMatcher compBaseMatcher;

    @Inject
    BigGroupMatcher bigGroupMatcher;

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
                return groupAlMatcher;
            }
            case SingleUser: {
                return bigGroupMatcher;
            }
        }
        return instance();
    }
}
