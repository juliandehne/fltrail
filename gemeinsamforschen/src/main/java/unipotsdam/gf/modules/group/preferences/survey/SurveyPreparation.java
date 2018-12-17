package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.group.preferences.excel.ItemsImporter;
import unipotsdam.gf.modules.project.Project;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;

public class SurveyPreparation {



    public static void main(String[] args) throws Exception {
        SurveyPreparationHelper surveyPreparationHelper = new SurveyPreparationHelper();
        surveyPreparationHelper.prepareSurvey();
    }
}
