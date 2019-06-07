package unipotsdam.gf.process.tasks;

public enum TaskName {
    // Teacher Tasks
    WAIT_FOR_PARTICPANTS, CLOSE_GROUP_FINDING_PHASE, WAITING_FOR_GROUP,
    CLOSE_DOSSIER_FEEDBACK_PHASE, WAIT_FOR_REFLECTION, FORM_GROUPS_MANUALLY,
    CLOSE_EXECUTION_PHASE,
    WAIT_FOR_PEER_ASSESSMENTS, FINALIZE_ASSESSMENT, CLOSE_ASSESSMENT_PHASE,

    // Student Tasks
    UPLOAD_DOSSIER, GIVE_FEEDBACK, SEE_FEEDBACK, CREATE_QUIZ, WRITE_EJOURNAL, ANNOTATE_DOSSIER, FINALIZE_DOSSIER,
    FINALIZE_EJOURNAL,
    EDIT_FORMED_GROUPS, CONTACT_GROUP_MEMBERS, ASSESSMENT, WAITING_FOR_STUDENT_DOSSIERS, EDIT_FEEDBACK,

    // Student ePortfolio Tasks
    OPTIONAL_PORTFOLIO_ENTRY, REFLECT_DOSSIER_CREATION, ANSWER_REFLECTION_QUESTIONS, COLLECT_RESULTS_FOR_ASSESSMENT,

    GIVE_ASSESSMENT, SEE_ASSESSMENT,
    END
}
