package unipotsdam.gf.modules.group;

/**
 * This type indicates how groups are affected by the underlying variable of the item
 * if it is homogenous, the groups can't have enough of it i.e. motivation
 *
 * If it is heteogenous, the groups should have contrasting members, i.e. more of a leader type but not too many
 * alphas
 *
 * If it is nivelled, it is an attribute best avoided beause it could be mission critial, such as a depressed person.
 * Here the groups should be greated in a way, that the problematic people are distributed in the groups
 */
public enum ProfileQuestionRelationType {
    HOMOGENOUS,
    HETEROGENOUS,
    NIVELLED
}
