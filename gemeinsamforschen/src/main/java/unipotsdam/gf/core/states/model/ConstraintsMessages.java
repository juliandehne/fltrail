package unipotsdam.gf.core.states.model;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

public class ConstraintsMessages {
    private final Constraints constraint;
    private final StudentIdentifier student;

    public ConstraintsMessages(Constraints enumConstraints, StudentIdentifier student) {
        this.constraint = enumConstraints;
        this.student = student;
    }

    @Override
    public String toString() {
        switch (constraint) {
            case QuizCount: return student.getStudentId()+" hat noch kein Quiz erstellt.";
            case DossierMissing: return student.getStudentId()+" hat noch kein Dossier hochgeladen.";
            case FeedbackCount: return student.getStudentId()+" hat noch nicht das erwartete Feedack gegeben.";
            case JournalCount: return student.getStudentId()+" hat noch nicht genug Tagebucheinträge verfasst.";
            case DossierOpen: return student.getStudentId()+" hat sein Dossier noch nicht finalisiert.";
            case DescriptionsOpen: return student.getStudentId()+" hat die Descriptions noch nicht abgeschlossen.";
            case JournalOpen: return student.getStudentId()+" hat sein Tagebuch noch nicht finalisiert";
            case AssessmentOpen: return student.getStudentId()+" hat die Bewertung noch nicht abgeschlossen.";
            default: return "default message";
        }
    }
}