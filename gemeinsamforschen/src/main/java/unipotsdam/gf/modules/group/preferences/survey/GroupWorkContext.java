package unipotsdam.gf.modules.group.preferences.survey;

public enum GroupWorkContext {
    // this is the context for fltrail normal run
    fl,
    // G_A Variablen ohne G_FL in gamer context
    dota_survey_a1,
    // G_A Variablen mit G_FL in gamer context
    dota_survey_a2,
    // G_A Variablen ohne G_FL in fl context
    fl_survey_a3,
    // G_A Variablen mit G_FL in fl context
    fl_survey_a4,
    // for the lab test with the dota group (with G_FL)
    dota,
    // local tests with dota people (a2)
    dota_test,
    // for tests with fl team / people
    fl_test,

    // seems outdated
    evaluation,

    // fl survey with prof. lausberg
    fl_lausberg,

    // fl survey with prof. lausberg
    fl_lausberg_test,

    //G_A Variablen ohne G_FL in other context
    other_survey_a1,

    // G_A Variablen mit G_FL in other context
    other_survey_a2,

    // fl survey with prof. lausberg
    fl_wedeman,

    // fl survey with prof. lausberg
    fl_wedeman_test,

}
