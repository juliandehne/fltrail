package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.group.preferences.survey.*;

import java.util.ArrayList;

public class InternalAssessmentQuestions {

    public ArrayList<QuestionData> getTheQuestions() {
        String coop1 = "Wie gut haben Sie sich im Team gegenseitig ergänzt und gemeinsam zum Erfolg beigetragen?";
        LocalizedText coop1Min = new LocalizedText("good addendum", "hohe Ergänzung");
        LocalizedText coop1Max = new LocalizedText("bad addendum", "schwache Ergänzung");
        String verstaendnis1 = "Wie oft hat dieses Teammitglied Fragen gestellt, wenn Dinge unklar waren?";
        LocalizedText verstaendnis1Min = new LocalizedText("often", "häufig");
        LocalizedText verstaendnis1Max = new LocalizedText("rarely", "selten");
        String zusammenarbeit2 = "Wie oft war Ihnen klar, wie die Aufgaben verteilt waren und was dieses " +
                "Teammitglied macht?";
        LocalizedText zusammenarbeit2Min = new LocalizedText("always", "immer");
        LocalizedText zusammenarbeit2Max = new LocalizedText("rarely", "selten");
        String problem1 = "Wie oft konnten Sie mit diesem Teammitglied strukturiert und systematisch arbeiten?";
        LocalizedText problem1Min = new LocalizedText("always", "immer");
        LocalizedText problem1Max = new LocalizedText("rarely", "selten");
        String verstaendnis2 = "Wie hat Ihnen dieses Teammitglied etwas erklärt, wenn etwas unklar war?";
        LocalizedText verstaendnis2Min = new LocalizedText("great explanations", "gute Erklärungen");
        LocalizedText verstaendnis2Max = new LocalizedText("no / bad explanations", "keine / schlechte Erklärungen");
        String problem2 = "Wie waren die Aufgaben verteilt?";
        LocalizedText problem2Min = new LocalizedText("great sharing", "sinnvolle / gute Verteilung");
        LocalizedText problem2Max = new LocalizedText("unfair / inefficient sharing", "unfaire / schlechte Verteilung");
        String zusammenarbeit3 = "Wie oft hat Ihr Teammitglied seine Vorgehensweise kommuniziert?";
        LocalizedText zusammenarbeit3Min = new LocalizedText("frequent communication", "häufige Kommunikation");
        LocalizedText zusammenarbeit3Max = new LocalizedText("no communication", "fehlende Kommunikation");
        String problem4 = "Wie oft hat sich dieses Mitglied an Absprachen bezüglich der Aufgabenteilung gehalten?";
        LocalizedText problem4Min = new LocalizedText("always", "immer");
        LocalizedText problem4Max = new LocalizedText("never", "nie");
        String zusammenarbeit1 = "Wie schätzen Sie die generelle Zusammenarbeit dieses Teammitglieds in Ihrer Gruppe ein?";
        LocalizedText zusammenarbeit1Min = new LocalizedText("great", "sehr gut");
        LocalizedText zusammenarbeit1Max = new LocalizedText("bad", "schlecht");

        ArrayList<QuestionData> result = new ArrayList<>();
        QuestionData questionData = new QuestionData(GroupWorkDimensions.COOPERATION, "coop1", coop1);
        questionData.setMinRating(coop1Min);
        questionData.setMaxRating(coop1Max);
        result.add(questionData);

        QuestionData questionData2 = new QuestionData(GroupWorkDimensions.COOPERATION, "zusammenarbeit1", zusammenarbeit1);
        questionData2.setMinRating(zusammenarbeit1Min);
        questionData2.setMaxRating(zusammenarbeit1Max);
        result.add(questionData2);

        QuestionData questionData3 = new QuestionData(GroupWorkDimensions.COOPERATION, "zusammenarbeit2",
                zusammenarbeit2);
        questionData3.setMinRating(zusammenarbeit2Min);
        questionData3.setMaxRating(zusammenarbeit2Max);
        result.add(questionData3);

        QuestionData questionData4 = new QuestionData(GroupWorkDimensions.COOPERATION, "zusammenarbeit3",
                zusammenarbeit3);
        questionData4.setMinRating(zusammenarbeit3Min);
        questionData4.setMaxRating(zusammenarbeit3Max);
        result.add(questionData4);

        QuestionData questionData5 = new QuestionData(GroupWorkDimensions.UNDERSTANDING, "verstaendnis1",
                verstaendnis1);
        questionData5.setMinRating(verstaendnis1Min);
        questionData5.setMaxRating(verstaendnis1Max);
        result.add(questionData5);

        QuestionData questionData6 = new QuestionData(GroupWorkDimensions.UNDERSTANDING, "verstaendnis2",
                verstaendnis2);
        questionData6.setMinRating(verstaendnis2Min);
        questionData6.setMaxRating(verstaendnis2Max);
        result.add(questionData6);

        QuestionData questionData7 = new QuestionData(GroupWorkDimensions.PROBLEM_SOLVING, "problem1",
                problem1);
        questionData7.setMinRating(problem1Min);
        questionData7.setMaxRating(problem1Max);
        result.add(questionData7);

        QuestionData questionData8 = new QuestionData(GroupWorkDimensions.PROBLEM_SOLVING, "problem2",
                problem2);
        questionData8.setMinRating(problem2Min);
        questionData8.setMaxRating(problem2Max);
        result.add(questionData8);

        QuestionData questionData9 = new QuestionData(GroupWorkDimensions.PROBLEM_SOLVING, "problem4",
                problem4);
        questionData9.setMinRating(problem4Min);
        questionData9.setMaxRating(problem4Max);
        result.add(questionData9);

        return result;
    }

    SurveyData getQuestionsInSurveyJSFormat() {
        SurveyData surveyData = new SurveyData();
        //surveyData.setTitle(new LocalizedText("Bewertung der Gruppenarbeit", "Bewertung der Gruppenarbeit" ));
        Page page = new Page();
        //page.setName("Bewertung der Gruppenarbeit");
        int i = 0;
        for (QuestionData questionData : getTheQuestions()) {
            if (i == 4) {
                surveyData.getPages().add(page);
                page = new Page();
                //page.setName("Bewertung der Gruppenarbeit");
            }
            page.getQuestions().add(convert(questionData));
            i++;
        }
        surveyData.getPages().add(page);
        return surveyData;
    }

    private Question convert(QuestionData questionData) {
        Question result;
        if (questionData.getMinRating() != null && questionData.getMaxRating() != null) {
            result = new ScaledQuestion(questionData.getMinRating(), questionData.getMaxRating());
        } else {
            result = new ScaledQuestion();
        }
        result.setIsRequired(true);
        result.setName(questionData.getKey());
        LocalizedText localizedText = new LocalizedText();
        localizedText.setDe(questionData.getQuestion());
        result.setTitle(localizedText);
        return result;
    }
}
