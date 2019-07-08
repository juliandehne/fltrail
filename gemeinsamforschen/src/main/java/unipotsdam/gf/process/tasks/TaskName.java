package unipotsdam.gf.process.tasks;

public enum TaskName {
    // Teacher Tasks
    WAIT_FOR_PARTICPANTS, CLOSE_GROUP_FINDING_PHASE, WAITING_FOR_GROUP,
    CLOSE_DOSSIER_FEEDBACK_PHASE, WAIT_FOR_REFLECTION, FORM_GROUPS_MANUALLY, INTRODUCE_E_PORTFOLIO_DOCENT,
    // execution phase
    WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION, CLOSE_EXECUTION_PHASE,
    CLOSE_PEER_ASSESSMENTS_PHASE, FINALIZE_ASSESSMENT, CLOSE_ASSESSMENT_PHASE, END_EXECUTION_PHASE,

    // Student Tasks
    UPLOAD_DOSSIER, GIVE_FEEDBACK, SEE_FEEDBACK, CREATE_QUIZ, WRITE_EJOURNAL, ANNOTATE_DOSSIER, FINALIZE_DOSSIER,
    FINALIZE_EJOURNAL, REEDIT_DOSSIER,

    ///////////////////////////////////
    // EXECUTION PHASE  tasks
    ////////////////////////////////////

    ///////////////////////////////////
    // STUDENT EXECUTION PHASE  tasks
    ////////////////////////////////////
    WAIT_FOR_LEARNING_GOALS, WORK_ON_LEARNING_GOALS, UPLOAD_LEARNING_GOAL_RESULT, ANSWER_REFLECTION_QUESTIONS, COLLECT_RESULTS_FOR_ASSESSMENT, WAIT_FOR_EXECUTION_PHASE_END,
    ///////////////////////////////////
    // DOCENT EXECUTION PHASE  tasks
    ////////////////////////////////////
    CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS, END_LEARNING_GOAL_PERIOD,

    EDIT_FORMED_GROUPS, CONTACT_GROUP_MEMBERS, WAITING_FOR_STUDENT_DOSSIERS, EDIT_FEEDBACK,

    //ASSESSMENT,

    // Student ePortfolio Tasks
    INTRODUCE_E_PORTFOLIO_STUDENT, REFLECT_DOSSIER_CREATION,

    ///////////////////////////////////
    // ASSESSMENT PHASE  tasks
    ////////////////////////////////////

    // students have to upload their presentation
    UPLOAD_PRESENTATION,
    // students have to upload their final report
    UPLOAD_FINAL_REPORT,
    // the docent waits for students to upload presentation
    WAIT_FOR_UPLOAD,

    // student gives assessment to other group
    GIVE_EXTERNAL_ASSESSMENT,

    // student rates his own group
    GIVE_INTERNAL_ASSESSMENT,

    // students wait for their grades to be done
    WAIT_FOR_GRADING,

    /////////////////////////////////////////////////////
    // GRADING PHASE TASKS
    /////////////////////////////////////////////////////

    // rate the contributions of the students
    GIVE_EXTERNAL_ASSESSMENT_TEACHER,

    // shared tasks

    // execution phase
    CHOOSE_FITTING_COMPETENCES, CHOOSE_REFLEXION_QUESTIONS,


    // the docent waits for students to grade their peers

    GIVE_FINAL_GRADES,
    END_DOCENT,
    END_STUDENT
}
