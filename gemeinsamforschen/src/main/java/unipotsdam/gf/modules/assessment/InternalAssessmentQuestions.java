package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.group.preferences.survey.LocalizedText;
import unipotsdam.gf.modules.group.preferences.survey.Page;
import unipotsdam.gf.modules.group.preferences.survey.Question;
import unipotsdam.gf.modules.group.preferences.survey.SurveyData;

import java.util.ArrayList;

public class InternalAssessmentQuestions {

    private ArrayList<QuestionData> getTheQuestions() {
        String coop1 = "Habt ihr euch im Team gegenseitig ergänzt und beide gemeinsam zum Erfolg beigetragen?" +
                "(1 für hohe Ergänzung, 5 für schwache)";
        String verstaendnis1 =  "Hat dieses Teammitglied Fragen gestellt, wenn Dinge unklar waren?" +
                "(1 für häufige Fragen, 5 für seltene)";
        String zusammenarbeit2 = "War Dir zu jedem Zeitpunkt klar, wie die Aufgaben verteilt wurden und was dieses " +
                "Teammitglied machen? (1 für immer und 5 für selten)";
        String problem1 = "Konntest du mit diesem Teammitglied strukturiert und systematisch arbeiten?" +
                "(1 für strukturiert, 5 für unstrukturiert)";
        String verstaendnis2 = "Hat dir dieses Teammitglied klar und deutlich erklärt, wenn etwas unklar " +
                "war? (1 für immer klar, wenn nötig; 5 für unklare oder fehlende Erklärungen)";
        String problem2 = "Habt ihr alle Aufgaben sinnvoll verteilt?" +
                "(1 für sinvolle Verteilung, 5 für unfaire oder ineffiziente Verteilung)";
        String zusammenarbeit3 = "Hat dein Teammitglied ausreichend häufig seine Vorgehensweise kommuniziert?" +
                "(1 für ausreichend häufige Kommunikation, 5 für fehlende Kommunikation)";
        String problem4 = "Hat sich dieses Mitglied an Absprachen bezüglich der Aufgabenteilung gehalten?" +
                "(1 für immer und 5 für nie)";
        String zusammenarbeit1 = "Wie schätzt Du die generelle Zusammenarbeit dieses Teammitglieds in deiner Gruppe " +
                "ein? (1 für sehr gut und 5 für schlecht)?";

        ArrayList<QuestionData> result = new ArrayList<>();
        QuestionData questionData = new QuestionData(GroupWorkDimensions.COOPERATION, "coop1", coop1);
        result.add(questionData);

        QuestionData questionData2 = new QuestionData(GroupWorkDimensions.COOPERATION, "zusammenarbeit1", zusammenarbeit1);
        result.add(questionData2);

        QuestionData questionData3 = new QuestionData(GroupWorkDimensions.COOPERATION, "zusammenarbeit2",
                zusammenarbeit2);
        result.add(questionData3);

        QuestionData questionData4 = new QuestionData(GroupWorkDimensions.COOPERATION, "zusammenarbeit3",
                zusammenarbeit3);
        result.add(questionData4);

        QuestionData questionData5 = new QuestionData(GroupWorkDimensions.UNDERSTANDING, "verstaendnis1",
                verstaendnis1);
        result.add(questionData5);

        QuestionData questionData6 = new QuestionData(GroupWorkDimensions.UNDERSTANDING, "verstaendnis2",
                verstaendnis2);
        result.add(questionData6);

        QuestionData questionData7 = new QuestionData(GroupWorkDimensions.PROBLEM_SOLVING, "problem1",
                problem1);
        result.add(questionData7);

        QuestionData questionData8 = new QuestionData(GroupWorkDimensions.PROBLEM_SOLVING, "problem2",
                problem2);
        result.add(questionData8);

        QuestionData questionData9 = new QuestionData(GroupWorkDimensions.PROBLEM_SOLVING, "problem4",
                problem4);
        result.add(questionData9);

        return result;
    }

    public SurveyData getQuestionsInSurveyJSFormat() {
        SurveyData surveyData = new SurveyData();
        Page page = new Page();
        page.setName("Bewertung der Gruppenarbeit");
        for (QuestionData questionData : getTheQuestions()) {
            page.getQuestions().add(convert(questionData));
        }
        surveyData.getPages().add(page);
        return surveyData;
    }

    private Question convert(QuestionData questionData) {
        Question result = new Question();
        result.setIsRequired(true);
        result.setName(questionData.getKey());
        LocalizedText localizedText = new LocalizedText();
        localizedText.setDe(questionData.getQuestion());
        result.setTitle(localizedText);
        return result;
    }
}
