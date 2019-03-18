package unipotsdam.gf.modules.group.preferences.survey;

import static unipotsdam.gf.config.GroupAlConfig.GROUPAL_SURVEY_COHORT_SIZE;

public class GroupWorkContextUtil {
    public static Boolean isSurveyContext(GroupWorkContext groupWorkContext) {
        return !groupWorkContext.equals(GroupWorkContext.fl) && !groupWorkContext.equals(GroupWorkContext.evaluation);
    }

    public static Boolean isGerman(GroupWorkContext groupWorkContext) {
        return decideLanguage(groupWorkContext);
    }

    /**
     * every cohort size (30) a new project is auto generated
     *
     * @param groupWorkContext
     * @return
     */
    public static Boolean isGamingOrAutomatedGroupFormation(GroupWorkContext groupWorkContext) {
        return groupWorkContext.toString().contains("dota");
    }

    /**
     * if the context ends with even number, have it as not FL item case
     *
     * @param groupWorkContext
     * @return
     */
    private static Boolean decideFLForOdd(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case evaluation:
            case fl:
                return true;
            default:
                int numberOfLab = Integer.parseInt(groupWorkContext.toString().replaceAll("[\\D]", ""));
                return (numberOfLab % 2 == 1);
        }
    }

    /**
     * if the context ends number higher then 4 it is english
     *
     * @param groupWorkContext
     * @return
     */
    private static Boolean decideLanguage(GroupWorkContext groupWorkContext) {
        switch (groupWorkContext) {
            case evaluation:
            case fl:
                return true;
            default:
                break;
        }
        int numberOfLab = Integer.parseInt(groupWorkContext.toString().replaceAll("[\\D]", ""));
        return (numberOfLab >= 4);
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
                return true;
            default:
                return decideFLForOdd(groupWorkContext);
        }
    }


    public static int getParticipantNeeded(GroupWorkContext context) {
        switch (context) {
            case fl:
                return -1;
            case dota_1:
            case dota_2:
            case dota_3:
            case dota_4:
            case dota_5:
                return GROUPAL_SURVEY_COHORT_SIZE;
            default:
                return 2;
        }
    }
}
