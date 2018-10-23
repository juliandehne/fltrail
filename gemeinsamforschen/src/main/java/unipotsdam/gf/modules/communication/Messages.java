package unipotsdam.gf.view;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.communication.model.EMailMessage;

public class Messages {
    public static EMailMessage GroupFormation(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getId() + ": Gruppenerstellung abgeschlossen");
        eMailMessage.setBody("Die Gruppen wurden für den Kurs " + project.getId() + " erstellt");
        return eMailMessage;
    }

    public static EMailMessage NewFeedbackTask(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getId() + ": Feedbackaufgabe erstellt");
        eMailMessage.setBody("Eine neue Feedbackaufgabe wurde für den Kurs " + project.getId() + " erstellt");
        return eMailMessage;
    }

    public static EMailMessage AssessmentPhaseStarted(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getId() + ": Beginn der Bewertungsphase");
        eMailMessage.setBody("Die Bewertungsphase hat begonnen. Bitte geht auf ... und macht ....");
        return eMailMessage;
    }

    public static EMailMessage CourseEnds(Project project) {
        // TODO add link to site + markup
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject(project.getId() + ": Bewertungsphase abgeschlossen");
        eMailMessage.setBody("Die Bewertung ist abgeschlossen. Sie erhalten ihre Bewertung in Kürze.");
        return eMailMessage;
    }
}
