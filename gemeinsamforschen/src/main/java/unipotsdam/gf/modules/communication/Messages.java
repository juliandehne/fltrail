package unipotsdam.gf.modules.communication;


import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.project.Project;

public class Messages {
    public static EMailMessage GroupFormation(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getName() + ": Gruppenerstellung abgeschlossen");
        eMailMessage.setBody("Die Gruppen wurden für den Kurs " + project.getName() + " erstellt");
        return eMailMessage;
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
}
