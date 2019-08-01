package unipotsdam.gf.process.constraints;

import unipotsdam.gf.modules.user.User;

public class ConstraintsMessages {
    private final Constraints constraint;
    private final User student;

    public ConstraintsMessages(Constraints enumConstraints, User student) {
        this.constraint = enumConstraints;
        this.student = student;
    }

    @Override
    public String toString() {
        switch (constraint) {
            case QuizCount:
                return student.getEmail() + " hat noch kein Quiz erstellt.";
            case DossierMissing:
                return student.getEmail() + " hat noch kein Dossier hochgeladen.";
            case FeedbackCount:
                return student.getEmail() + " hat noch nicht das erwartete Feedack gegeben.";
            case JournalCount:
                return student.getEmail() + " hat noch nicht genug Tagebucheinträge verfasst.";
            case DossierOpen:
                return student.getEmail() + " hat sein Dossier noch nicht finalisiert.";
            case DescriptionsOpen:
                return student.getEmail() + " hat die Descriptions noch nicht abgeschlossen.";
            case JournalOpen:
                return student.getEmail() + " hat sein Tagebuch noch nicht finalisiert";
            case AssessmentOpen:
                return student.getEmail() + " hat die Bewertung noch nicht abgeschlossen.";
            default: return "default message";
        }
    }
}
