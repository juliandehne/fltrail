package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SurveyMapper {

    @Inject
    ProfileDAO profileDAO;

    /**
     * generate the backing data for the survey
     * @param groupWorkContext
     * @param standalone is false if run within fltrail general project
     * @return
     */
    public SurveyData getItemsFromDB(GroupWorkContext groupWorkContext, Boolean standalone, String testProject) {
        SurveyData surveyData = new SurveyData(); // the result obj

        // the persisted questions from the excel sheet (ITEMS for FL, based on FideS Team research)
        List<ProfileQuestion> questions = profileDAO.getSelectedQuestions(testProject);


        if (standalone) {

            // the general questions to create a profile (given that we are not running the survey as part of the normal
            // FL-Trail Mode
            List<LocalizedText> generalTextQuestions = new ArrayList<>();
            generalTextQuestions.add(new LocalizedText("Choose a nickname!", "Wählen Sie einen Nickname aus!"));
            generalTextQuestions.add(new LocalizedText("Repeat your nickname!", "Wiederholen Sie den Nickname!"));
            generalTextQuestions.add(new LocalizedText("Enter a valid email!", "Enter a valid Email!"));

            switch (groupWorkContext) {
                case FL:
                    break;
                case DOTA:
                case OVERWATCH:
                    generalTextQuestions.add(new LocalizedText("" + "(optional) Enter your discord ID!",
                            "(optional) Geben Sie ihre Discord ID ein!"));
                    break;
            }

            // adapt title
            surveyData.setTitle(new LocalizedText(
                    "Thank you for participating in this survey about good group formation",
                    "Vielen Dank für ihre Bereitschaft zur Teilnahme an dieser Befragung"));

            Page generalDetails = new Page();
            generalDetails.setName("general1");
            for (LocalizedText generalTextQuestion : generalTextQuestions) {
                OpenQuestion openQuestion = new OpenQuestion();
                openQuestion.setTitle(generalTextQuestion);
                generalDetails.getQuestions().add(openQuestion);
            }
            surveyData.getPages().add(generalDetails);


            Page profileQuestionsPage = new Page();
            int i = 0;
            for (ProfileQuestion question : questions) {
                // just those things
                if (i == 5) {
                    surveyData.getPages().add(profileQuestionsPage);
                    profileQuestionsPage = new Page();
                }
                ScaledQuestion scaledQuestion = convertQuestion(question);
                profileQuestionsPage.getQuestions().add(scaledQuestion);
            }
            if (!profileQuestionsPage.getQuestions().isEmpty()) {
                surveyData.getPages().add(profileQuestionsPage);
            }
        }



        return surveyData;
    }

    public static ScaledQuestion convertQuestion(ProfileQuestion question) {
        ScaledQuestion scaledQuestion = new ScaledQuestion();
        scaledQuestion.setName(question.getSubvariable() + question.getId());
        scaledQuestion.setTitle(new LocalizedText(question.getQuestion_en(), question.getQuestion()));
        return scaledQuestion;
    }
}
