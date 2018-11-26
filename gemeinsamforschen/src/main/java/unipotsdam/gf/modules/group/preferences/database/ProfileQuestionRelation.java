package unipotsdam.gf.modules.group.preferences.database;

public class ProfileQuestionRelation {
    private ProfileQuestion profileQuestion1;
    private ProfileQuestion getProfileQuestion2;
    private ProfileQuestionRelationType profileQuestionRelationType;

    public ProfileQuestionRelation(
            ProfileQuestion profileQuestion1, ProfileQuestion getProfileQuestion2,
            ProfileQuestionRelationType profileQuestionRelationType) {
        this.profileQuestion1 = profileQuestion1;
        this.getProfileQuestion2 = getProfileQuestion2;
        this.profileQuestionRelationType = profileQuestionRelationType;
    }

    public ProfileQuestionRelation() {
    }

    public ProfileQuestion getProfileQuestion1() {
        return profileQuestion1;
    }

    public void setProfileQuestion1(ProfileQuestion profileQuestion1) {
        this.profileQuestion1 = profileQuestion1;
    }

    public ProfileQuestion getGetProfileQuestion2() {
        return getProfileQuestion2;
    }

    public void setGetProfileQuestion2(ProfileQuestion getProfileQuestion2) {
        this.getProfileQuestion2 = getProfileQuestion2;
    }

    public ProfileQuestionRelationType getProfileQuestionRelationType() {
        return profileQuestionRelationType;
    }

    public void setProfileQuestionRelationType(
            ProfileQuestionRelationType profileQuestionRelationType) {
        this.profileQuestionRelationType = profileQuestionRelationType;
    }
}
