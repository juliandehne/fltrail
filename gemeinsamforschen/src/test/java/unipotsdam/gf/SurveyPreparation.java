package unipotsdam.gf;

import unipotsdam.gf.core.database.UpdateDB;

public class SurveyPreparation {

    public static void main(String[] args) throws Exception {


        UpdateDB.main(new String[0]);

        SurveyPreparationHelper surveyPreparationHelper = new SurveyPreparationHelper();
        surveyPreparationHelper.prepareSurvey();
    }
}
