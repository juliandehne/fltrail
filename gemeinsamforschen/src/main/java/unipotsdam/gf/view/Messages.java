package unipotsdam.gf.view;

import unipotsdam.gf.core.management.project.Project;

public class Messages {
    public static String GroupFormation(Project project){
        // TODO add link to site + markup
        return "Die Gruppen wurden für den Kurs "+ project.getId() + " erstellt";
    }

    public static String NewFeedbackTask(Project project) {
        // TODO add link to site + markup
        return "Eine neue Feedbackaufgabe wurde für den Kurs "+ project.getId() + " erstellt";
    }

    public static String AssessmentPhaseStarted(Project project) {
        // TODO add link to site + markup
        return "Die Bewertungsphase hat begonnen. Bitte geht auf ... und macht ....";
    }

    public static String CourseEnds(Project project) {
        // TODO add link to site + markup
        return "Die Bewertung ist abgeschlossen. Sie erhalten ihre Bewertung in Kürze.";
    }
}
