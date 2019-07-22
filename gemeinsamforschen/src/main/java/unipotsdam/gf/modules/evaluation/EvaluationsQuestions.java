package unipotsdam.gf.modules.evaluation;

import unipotsdam.gf.modules.group.preferences.survey.*;

import java.util.HashMap;
import java.util.Set;

public class EvaluationsQuestions {
    private HashMap<String, String> getSUS() {
        HashMap<String, String> result = new HashMap<>();
        result.put("q1", "Ich kann mir sehr gut vorstellen, das System regelmäßig zu nutzen.");
        result.put("q2", "Ich empfinde das System als unnötig komplex.");
        result.put("q3", "Ich empfinde das System als einfach zu nutzen.");
        result.put("q4", "Ich denke, dass ich technischen Support brauchen würde, um das System zu nutzen.");
        result.put("q5", "Ich finde, dass die verschiedenen Funktionen des Systems gut integriert sind.");
        result.put("q6", "Ich finde, dass es im System zu viele Inkonsistenzen gibt.");
        result.put("q7", "Ich kann mir vorstellen, dass die meisten Leute das System schnell zu beherrschen lernen.");
        result.put("q8", "Ich empfinde die Bedienung als sehr umständlich.");
        result.put("q9", "Ich habe mich bei der Nutzung des Systems sehr sicher gefühlt.");
        result.put("q10", "Ich musste eine Menge Dinge lernen, bevor ich mit dem System arbeiten konnte.");
        return result;
    }
    public HashMap<String,Boolean> getPolarityMap() {
        HashMap<String,Boolean> result = new HashMap<>();
        Set<String> strings = getSUS().keySet();
        Boolean last = true;
        for (String string : strings) {
            result.put(string, !last);
        }
        return result;
    }

    public SurveyData getSusSurveyData() {
        SurveyData surveyData = new SurveyData();
        HashMap<String, String> sus = getSUS();
        int i = 0;
        Page page = new Page();
        page.setName("Evaluationsfragen");
        for (String s : sus.keySet()) {
            i++;
            if (i == 5) {
                i = 0;
                surveyData.getPages().add(page);
                page = new Page();
                page.setName("Evaluationsfragen");
            }
            ScaledQuestion question = null;
            if (!getPolarityMap().get(s)) {
                String minDescription = "Ich stimme eher nicht zu";
                String maxDescription = "Ich stimme eher zu";
                question = new ScaledQuestion(
                        new LocalizedText(minDescription, minDescription),
                        new LocalizedText(maxDescription,maxDescription));
            } else {
                String minDescription = "Ich stimme eher zu";
                String maxDescription = "Ich stimme eher nicht zu";
                question = new ScaledQuestion(
                        new LocalizedText(minDescription, minDescription),
                        new LocalizedText(maxDescription,maxDescription));
            }
            LocalizedText localizedText = new LocalizedText(sus.get(s),sus.get(s));
            question.setTitle(localizedText);
            question.setName(s);
            page.getQuestions().add(question);
        }

        return surveyData;
    }
}
