let welcomeTextDE = "Vielen Dank, für Ihr Interesse an diesem Experiment teil zu nehmen. Es geht um Gruppenbildung" +
    " basierend auf verschiedenen Kriterien. Hierzu gehen Sie bitte auf die \"Umfragen-Seite\" und beantworten die" +
    " dortigen Fragen. Dies dauert etwa 10 Minuten. Sobald 30 Teilnehmer die Umfrage ausgefüllt haben, werden" +
    " Gruppen gebildet, die Sie dann unter dem Reiter \"Gruppen\" finden werden. So lange noch keine 30 Teilnehmer" +
    " den Fragebogen ausgefüllt haben, ist die \"Gruppen-Seite\" leer. <br> Vielen Dank für Ihre Teilnahme!";

let welcomeTextEN = " Thank you for your interest and your participation in our survey." +
    "            We are here to build and evaluate groups based on different criteria. Therefore use the\n" +
    "            \"survey-page\" and answer the questions. This will last about 10 minutes." +
    "            When 30 people committed their answers, you can see which group you are in on the \"group-page\".\n" +
    "            As long as there are not enough participants, you can't see your group." +
    "            Thank you for your participation!";

let welcomeTitleDE = "Willkommen";
let welcomeTitleEN = "Welcome";


let navEN = [{
    groups: "Groups",
    introduction: "Introduction",
    survey: "Survey",
    persist: "Admin",
    logout: "log out"
}];

let navDE = [{
    groups: "Gruppen",
    introduction: "Einleitung",
    survey: "Umfrage",
    persist: "Admin",
    logout: "ausloggen"
}];

let groupViewLoginEN = [{
    enterEmail: "Please enter your Email here:",
    submit: "submit",
    emailDoesntExist: "This Email does not exists. Please participate in the survey first."
}];

let groupViewLoginDE = [{
    enterEmail: "Bitte geben sie ihre Email hier ein:",
    submit: "bestätigen",
    emailDoesntExist: "Diese Email existiert nicht. Bitte nehmen Sie an der Umfrage teil."
}];

let computedGroupsEN = [{
    computedGroups: "Computed Groups",
}];

let computedGroupsDE = [{
    computedGroups: "Gruppeneinteilung",
}];


function noGroupsMessageEN(participantsNeeded) {
    let noGroupsEN = [{
        noGroupsYet: "There are no groups built yet.",
        participantsMissing: messageParticipantsNeededEN(participantsNeeded),
        comeBackAfterMail: "Please come back to this page after you get an E-Mail, that groups where built.",
    }];
    return noGroupsEN;
}

function noGroupsMessageDE(participantsNeeded) {
    let noGroupsDE = [{
        noGroupsYet: "Es wurden noch keine Gruppen gebildet.",
        participantsMissing: messageParticipantsNeededDE(participantsNeeded),
        comeBackAfterMail: "Kommen sie auf diese Seite zurück nachdem sie eine EMail bekommen haben.",
    }];
    return noGroupsDE;
}

function messageParticipantsNeededDE(participantsNeeded) {
    return "Es fehlen noch " + participantsNeeded + " Teilnehmer, um Gruppen zu bilden.";
}

function messageParticipantsNeededEN(participantsNeeded) {
    return "Es fehlen noch " + participantsNeeded + " Teilnehmer um die Gruppen zu bilden.";
}