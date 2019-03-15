package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.group.Group;

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
            case fl_test:
            case dota_survey_a1:
            case dota_test:
            case dota_survey_a2:
                return false;
            default:
                return decideLanguage(groupWorkContext);
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
            case fl_test:
            case dota_survey_a1:
            case dota_test:
                return true;
            default:
                return decideLanguage(groupWorkContext);
        }
    }

    /**
     * every cohort size (30) a new project is auto generated
     * @param groupWorkContext
     * @return
     */
    public static Boolean isGamingOrAutomatedGroupFormation(GroupWorkContext groupWorkContext) {
        return groupWorkContext.toString().contains("dota");
    }

    /**
     * if the context ends with even number, have it as not FL item case
     * @param groupWorkContext
     * @return
     */
    private static Boolean decideFLForOdd(GroupWorkContext groupWorkContext) {
        return (Integer.parseInt(groupWorkContext.toString()) % 2 == 1);
    }

    /**
     * if the context ends number higher then 4 it is english
     * @param groupWorkContext
     * @return
     */
    private static Boolean decideLanguage(GroupWorkContext groupWorkContext) {
        return (Integer.parseInt(groupWorkContext.toString()) >= 4);
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
            case dota_survey_a1:
                return true;
            default:
                return decideFLForOdd(groupWorkContext);
        }
    }



    public static int getParticipantNeeded(GroupWorkContext context) {
        switch (context) {
            case fl:
                return -1;
            case dota_survey_a1:
            case dota_survey_a2:
            case dota:
            case dota_test:
                return 30;
            default:
                return 2;
        }
    }
}
