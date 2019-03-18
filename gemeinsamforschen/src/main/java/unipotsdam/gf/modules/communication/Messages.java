package unipotsdam.gf.modules.communication;


import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContextUtil;
import unipotsdam.gf.modules.project.Project;

public class Messages {


    public static EMailMessage GroupFormation(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getName() + ": Gruppenerstellung abgeschlossen");
        eMailMessage.setBody("Die Gruppen wurden für den Kurs " + project.getName() + " erstellt.");
        return eMailMessage;
    }

    public static EMailMessage SurveyGroupFormation(Project project, String userEmail) {
        userEmail = stringToAsciiEncode(userEmail);
        // deutsch
        StringBuilder message_de = new StringBuilder();
        message_de.append("Liebe Teilnehmenden,\n");
        message_de.append("die Gruppen wurden gebildet.");
        message_de
                .append("Sie können unter http://fleckenroller.cs.uni-potsdam" + ".de/app/gemeinsamforschen/survey/enterGFM.jsp?context=" + project
                        .getGroupWorkContext()+"&userEmail="+userEmail);
        message_de.append("&language=de ");
        message_de.append("angeschaut werden.");
        message_de.append("\nMit besten Grüßen, \n Julian Dehne");

        // englisch
        StringBuilder message_en = new StringBuilder();
        message_en.append("Dear participants,\n");
        message_en.append("the groups have been formed.");
        message_en
                .append(" They can be looked up at http://fleckenroller.cs.uni-potsdam" + "" +
                        ".de/app/gemeinsamforschen/survey/enterGFM.jsp?context=" + project
                        .getGroupWorkContext()+"&userEmail="+userEmail+"&language=en");
        message_en.append("&language=en ");
        message_en.append(".");
        message_en.append("\nYours sincerly, \n Julian Dehne");

        String subject = "Gruppen wurden gebildet / groups have been formed [" + project.getGroupWorkContext()
                .toString() + "]";
        String body = message_de.toString() + "\n" + message_en.toString();

        body = switchLanguage(project, message_de, message_en, body);

        return new EMailMessage(subject, body);
    }

    public static EMailMessage SurveyEvaluation(Project project) {

        String surveyUrl = "http://fleckenroller.cs.uni-potsdam.de/limesurvey/index.php/survey/index/sid/957872/newtest/Y/lang/de";
        String survey_enUrl = "http://fleckenroller.cs.uni-potsdam.de/limesurvey/index.php/survey/index/sid/356615/newtest/Y/lang/en";

        StringBuilder message_de = new StringBuilder();
        message_de.append("Liebe Teilnehmenden,\n");
        message_de.append("Danke für die Teilnahme an der Studie zur Gruppenfindung. Bitte füllt diesen " +
                "Evaluationsbogen" +
                "aus:  ");
        message_de.append(surveyUrl);
        message_de.append("\nMit besten Grüßen, \n Julian Dehne");

        StringBuilder message_en = new StringBuilder();
        message_en.append("Dear participants, \n thank you for your engagment in this project. Please follow the link" +
                " to the evaluation survey and fill it out: ");
        message_en.append(survey_enUrl);
        message_en.append("\nYours sincerly, \n Julian Dehne");

        String subject = "Gruppenbewertung / group work evaluation [" + project.getGroupWorkContext().toString() + "]";
        String body = message_de.toString() + "\n" + message_en.toString();

        body = switchLanguage(project, message_de, message_en, body);

        return new EMailMessage(subject, body);
    }

    private static String switchLanguage(
            Project project, StringBuilder message_de, StringBuilder message_en, String body) {
        if(GroupWorkContextUtil.isGerman(project.getGroupWorkContext())) {
            return message_de.toString();
        }
        if (!GroupWorkContextUtil.isGerman(project.getGroupWorkContext())) {
            return message_en.toString();
        }
        return body;
    }


    public static EMailMessage NewFeedbackTask(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getName() + ": Feedbackaufgabe erstellt");
        eMailMessage.setBody("Eine neue Feedbackaufgabe wurde für den Kurs " + project.getName() + " erstellt");
        return eMailMessage;
    }

    public static EMailMessage AssessmentPhaseStarted(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getName() + ": Beginn der Bewertungsphase");
        eMailMessage.setBody("Die Bewertungsphase hat begonnen. Bitte geht auf ... und macht ....");
        return eMailMessage;
    }

    public static EMailMessage CourseEnds(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getName() + ": Bewertungsphase abgeschlossen");
        eMailMessage.setBody("Die Bewertung ist abgeschlossen. Sie erhalten ihre Bewertung in Kürze.");
        return eMailMessage;
    }

    public static String stringToAsciiEncode(String input) {
        String result = "";
        for (Character character : input.toCharArray()) {
            int ascii = (int) character;
            String rChar = ascii+"-";
            result += rChar;
        }
        return result.substring(0, result.length()-1);
    }
}
