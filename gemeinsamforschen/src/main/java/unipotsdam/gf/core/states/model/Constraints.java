package unipotsdam.gf.core.states.model;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

public class Constraints {

    public static String DossierWritten(StudentIdentifier student) {
        return student.getStudentId() + " hat noch kein Dossier hochgeladen.";
    }

    public static String FeedbackCount(StudentIdentifier student) {
        return student.getStudentId() + " hat noch nicht das erwartete Feedack gegeben.";
    }

    public static String QuizCount(StudentIdentifier student) {
        return student.getStudentId() + " hat noch kein Quiz erstellt.";
    }

    public static String JournalCount(StudentIdentifier student) {
        return student.getStudentId() + " hat noch nicht genug Tagebucheinträge verfasst.";
    }

    public static String DossierFinalized(StudentIdentifier student) {
        return student.getStudentId() + " hat sein Dossier noch nicht finalisiert.";
    }

    public static String JournalFinalized(StudentIdentifier student) {
        return student.getStudentId() + " hat sein Tagebuch noch nicht finalisiert";
    }

    public static String AssessmentDone(StudentIdentifier student) {
        return student.getStudentId() + " hat die Bewertung noch nicht abgeschlossen.";
    }
}
