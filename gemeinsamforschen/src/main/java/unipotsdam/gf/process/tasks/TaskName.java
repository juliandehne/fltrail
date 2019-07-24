package unipotsdam.gf.process.tasks;

import java.util.ArrayList;

public enum TaskName {

    //TEACHER:
    // open project,
    // define a name,
    // choose a groupFindingAlgorithm,
    // describe your project,
    // choose parts (categories), which students have to complete during your project

    //Gruppenbildung
    /////////////////////////////////////////////////////////////
    //TEACHER:
    // how to trigger: open project
    // how to solve: wait for students to participate.
    //               check groups built by algorithm and save final groups.
    WAIT_FOR_PARTICPANTS,
    //STUDENT:
    // how to trigger: enter a project
    // how to solve: wait for teacher to assign a group to you
    WAITING_FOR_GROUP,
    //TEACHER:
    // how to trigger: saved final groups.
    // how to solve: click button to end "Gruppenbildung" and start "Entwurf"
    CLOSE_GROUP_FINDING_PHASE,


    //Entwurf
    ////////////////////////////////////////////////////////////
    //STUDENT:
    // how to trigger: teacher started phase "Entwurf"
    // how to solve: use chat to contact your groupMembers
    CONTACT_GROUP_MEMBERS,
    //TEACHER:
    // how to trigger: teacher started phase "Entwurf"
    // how to solve: wait for groups to upload first version of dossier
    //               wait for groups to feedback their peers dossier
    //               wait for groups to upload second version of dossier
    //               wait for groups to finalize second version of dossier
    WAITING_FOR_STUDENT_DOSSIERS,
    //STUDENT:
    // how to trigger: teacher started phase "Entwurf"
    // how to solve: write first version of a dossier for your whole group.
    //               group can reedit first version until "ANNOTATE_DOSSIER" was finished.
    UPLOAD_DOSSIER,
    //STUDENT:
    // how to trigger: teacher started phase "Entwurf"
    // how to solve: in the top menu, you can click E-Portfolio and write entries
    //               task ends in phase "Durchführung" after finalizing it todo: @Martin
    INTRODUCE_E_PORTFOLIO_STUDENT,
    //STUDENT:
    // how to trigger: upload first version of dossier
    // how to solve: assign text to every annotation category.
    //               completing this task also closes "UPLOAD_DOSSIER"
    ANNOTATE_DOSSIER,
    //STUDENT:
    // how to trigger: annotate first version of dossier
    // how to solve: read dossier of another group and feedback every annotated category.
    GIVE_FEEDBACK,
    //STUDENT:
    // how to trigger: annotate first version of dossier AND wait for feedback
    // how to solve: task can not be solved
    SEE_FEEDBACK,
    //STUDENT:
    // how to trigger: give feedback to another dossier AND annotate first version of own dossier
    // how to solve: after editing dossier, finalize it by clicking "finale Abgabe" and save.
    REEDIT_DOSSIER,
    //TEACHER:
    // how to trigger: after uploading dossier, e-portfolio is
    // how to solve: in the top menu, you can click E-Portfolio and see entries of student and you can comment them
    //                   task ends in phase "Durchführung" after finalizing it todo: @Martin
    INTRODUCE_E_PORTFOLIO_DOCENT,
    //TEACHER:
    // how to trigger: all students uploaded final second version of dossier
    // how to solve: click button to end "Entwurf" and start "Durchführung"
    CLOSE_DOSSIER_FEEDBACK_PHASE,


    //Durchfuhrung
    ////////////////////////////////////////////////////////////
    //STUDENT:
    // how to trigger: start the execution phase
    // how to solve: docent needs to solve CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS task and has to start learning goal period
    WAIT_FOR_LEARNING_GOAL_TO_START,
    // how to trigger: docent starts a learning goal period
    // how to solve: docent ends learning goal period
    WORK_ON_LEARNING_GOAL,
    // how to trigger: docent ends learning goal period
    // how to solve: click on "Lernziel-Ergebnisse hochladen" and save result
    UPLOAD_LEARNING_GOAL_RESULT,
    // how to trigger: save/upload learning goal results
    // how to solve: click on "Reflexionsfragen beantworten" and answer all reflection questions
    ANSWER_REFLECTION_QUESTIONS,
    // how to trigger: after answering all reflection questions it is triggered, so student needs to wait for other students to finish task
    // how to solve: all other students are finished answering questions
    WAIT_FOR_OTHER_STUDENTS_FINISH_REFLECTION,
    // how to trigger: docent ends the learning goal and  all learning goals are finished
    // how to solve: click on "Wähle deine ... TODO: after implementation"
    CHOOSE_ASSESSMENT_MATERIAL,
    // how to trigger: choosing assessment material and save it
    // how to solve: the docent finishes the execution phase
    WAIT_FOR_EXECUTION_PHASE_END,
    //TEACHER:
    // how to trigger: start of the execution phase
    // how to solve: click on "Lernziele und Reflexionziele erstellen"
    CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS,
    // how to trigger: the docent chose learning goals and reflection questions
    // how to solve: click on "Lernzielarbeit beginnen"
    START_LEARNING_GOAL_PERIOD,
    // how to trigger: the docent started the work on the learning goal
    // how to solve: click on "Lernzielarbeit beenden" // it is only possible after the learning goal period is over (maybe)
    END_LEARNING_GOAL_PERIOD,
    // how to trigger: the docent ends learning goal period
    // how to solve: all students needs to upload learning goal results and answer reflection questions
    WAIT_FOR_REFLECTION,
    // how to trigger: all students are done uploading learning goals and answering reflection questions and another learning goal is
    // how to solve: it is solved automatically as it is only for information
    PREVIOUS_LEARNING_GOAL_DONE,
    // how to trigger: all learning goal results are uploaded and reflection questions are answered, all learning goals are finished
    // how to solve: all students need to choose the material for assessment
    WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION,
    // how to trigger: all students chose the material for assessment
    // how to solve: docent needs to click "Durchführungsphase beenden"
    CLOSE_EXECUTION_PHASE,

    // is used in assessment, so temp here because dont know if it can be removed
    CHOOSE_REFLEXION_QUESTIONS,

    //Bewertung
    ////////////////////////////////////////////////////////////
    //STUDENT:
    // how to trigger: teacher started phase "Bewertung"
    // how to solve:  upload a .pdf or .pptx file as your final presentation
    UPLOAD_PRESENTATION,
    //STUDENT:
    // how to trigger: teacher started phase "Bewertung"
    // how to solve: wait for groups to upload Presentation and report
    //               stop possibility for students to upload final presentation / report
    WAIT_FOR_UPLOAD,
    //STUDENT:
    // how to trigger: your group uploaded their final presentation
    // how to solve: upload a .pdf file as your final report to the project
    UPLOAD_FINAL_REPORT,
    //STUDENT:
    // how to trigger: teacher stopped uploads / finished task "WAIT_FOR_UPLOAD"
    // how to solve: assess another groups final presentation / report / dossier
    GIVE_EXTERNAL_ASSESSMENT,
    //STUDENT:
    // how to trigger: you assess another groups work
    // how to solve: assess your own groups work attitude and cooperation
    GIVE_INTERNAL_ASSESSMENT,
    //TEACHER:
    // how to trigger: teacher stopped waiting for uploads
    // how to solve: wait for students to asses peers (internal and external)
    //               stop possibility for students to asses peers
    COLLECT_RESULTS_FOR_ASSESSMENT,
    //TEACHER:
    // how to trigger: teacher stopped peer assessment / finished task "COLLECT_RESULTS_FOR_ASSESSMENT"
    // how to solve: click button to end "Bewertung" and start "Projektabschluss"
    CLOSE_PEER_ASSESSMENTS_PHASE,

    //Projektabschluss
    ////////////////////////////////////////////////////////////
    //STUDENT:
    // how to trigger: teacher started "Projektabschluss"
    // how to solve: wait for teacher to assess group work and give marks
    WAIT_FOR_GRADING,
    //TEACHER:
    // how to trigger: teacher started "Projektabschluss"
    // how to solve: teacher rates every groups dossier / presentation / report
    GIVE_EXTERNAL_ASSESSMENT_TEACHER,
    //TEACHER:
    // how to trigger: teacher rated all dossiers / presentations / reports
    // how to solve: see marks of peers for uploads, marks of teacher for uploads and marks of peers for group work
    //               decide for final grades, check "final grades" checkbox and save
    GIVE_FINAL_GRADES,
    //TEACHER:
    // how to trigger: teacher gave final grades
    // how to solve: click button to close "Projektabschluss"
    CLOSE_ASSESSMENT_PHASE,
    //STUDENT:
    // how to trigger: "Projektabschluss" was closed
    // how to solve: can't be solved
    END_STUDENT,
    //TEACHER:
    // how to trigger: "Projektabschluss" was closed
    // how to solve: can't be solved
    END_DOCENT,


    /// evaluation
    EVALUATION_TECHNISCH,

    EVALUATION_PROZESS;

    public static ArrayList<TaskName> getPersistentExecutionTasks() {
        ArrayList<TaskName> persistentTasks = new ArrayList<>();
        persistentTasks.add(CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS);
        persistentTasks.add(WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION);
        persistentTasks.add(CHOOSE_ASSESSMENT_MATERIAL);
        persistentTasks.add(WAIT_FOR_EXECUTION_PHASE_END);
        persistentTasks.add(CLOSE_EXECUTION_PHASE);

        return persistentTasks;

    }
}
