package unipotsdam.gf.modules.group.preferences.survey;

public class GroupWorkContextUtil {
    public static Boolean isSurveyContext(GroupWorkContext groupWorkContext) {
        return !groupWorkContext.equals(GroupWorkContext.fl) && !groupWorkContext.equals(GroupWorkContext.evaluation);
    }

    /**
     * checks if (email) messages should be send in English
     *
     * @param groupWorkContext
     * @return
     */
    public static Boolean isEnglish(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case dota_survey_a2:
                return true;
            default:
                return false;
        }
    }

    /**
     * checks if (email) message should
     *
     * @param groupWorkContext
     * @return
     */
    public static Boolean messageInEnglishAndGerman(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case fl_lausberg:
            case fl_lausberg_test:
            case fl_test:
            case other_survey_a1:
            case other_survey_a2:
            case dota_survey_a1:
            case dota_test:
            case dota_survey_a2:
            case fl_wedeman:
            case fl_wedeman_test:
                return false;
            default:
                return true;
        }
    }

    /**
     * messages (email) should be send in German for this lab context
     *
     * @param groupWorkContext
     * @return
     */
    public static Boolean isGerman(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case fl_lausberg:
            case fl_lausberg_test:
            case fl_test:
            case other_survey_a1:
            case other_survey_a2:
            case dota_survey_a1:
            case dota_test:
            case fl_wedeman:
            case fl_wedeman_test:
                return true;
            default:
                return true;
        }
    }

    /**
     * Some of the lab tests use the full set of items (for fl and general group work)
     *
     * @param groupWorkContext
     * @return
     */
    public static Boolean usesFullSetOfItems(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case fl:
            case dota:
            case dota_survey_a2:
            case fl_survey_a4:
            case fl_test:
            case fl_lausberg:
            case fl_lausberg_test:
            case dota_survey_a1:
                return true;
            default:
                return false;
        }
    }

    public static Boolean isAutomatedGroupFormation(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case other_survey_a2:
            case dota_survey_a1:
            case dota_survey_a2:
            case dota:
            case dota_test:
            case evaluation:
                return true;
            default:
                return false;
        }
    }
}
