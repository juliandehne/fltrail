let welcomeTextDE = "Vielen Dank, für Ihr Interesse an diesem Experiment teilzunehmen. Es geht um Gruppenbildung" +
    " basierend auf verschiedenen Kriterien. Hierzu gehen Sie bitte auf die Profilseite und beantworten die" +
    " dortigen Fragen. Dies dauert etwa 10 Minuten. Sobald 30 Teilnehmer die Umfrage ausgefüllt haben, werden" +
    " Gruppen gebildet, die Sie dann unter dem Reiter Gruppen finden werden. So lange noch keine 30 Teilnehmer" +
    " den Fragebogen ausgefüllt haben, ist die Gruppenseite leer. Bitte beachten Sie, dass die E-Mail anderen " +
    " Teilnehmern gezeigt wird. Sollte dies für Sie unangenehm sein, empfehlen wir, eine alternative" +
    " E-Mailadresse bei web.de oder ähnlichem Anbieter einzurichten." +
    " Vielen" +
    " Dank für Ihre Teilnahme!";

let welcomeTextEN = " Thank you for your interest and your participation in our survey." +
    "            We are here to build and evaluate groups based on different criteria. Please use the" +
    "            profile page and answer the questions. This will take about 10 minutes." +
    "            After 30 people have committed their answers, you can see which group you are in on the group page." +
    "            As long as there are not enough participants, you can't see your group. Please note that your email" +
    " will be visible to other participants. If that is a problem, please create an alternative email account like" +
    " web.de."  +
    "            Thank you for your participation!";

let welcomeTextFlEN = "Thank you for your interest and your participation in our survey." +
    "     We are here to build and evaluate groups based on different criteria. Please use the" +
    "     profile page and answer the questions. This will take about 10 minutes." +
    "     After groups are formed by your docent you can see all groups on the group page. " +
    "     Please note that your email" +
    "     will be visible to other participants. If that is a problem please create an alternative email account like" +
    "     web.de." +
    "     Thank you for your participation!";

let welcomeTextFlDE = "Vielen Dank, für Ihr Interesse an diesem Experiment teilzunehmen. Es geht um Gruppenbildung" +
    "     basierend auf verschiedenen Kriterien. Hierzu gehen Sie bitte auf die Profilseite und beantworten die" +
    "     dortigen Fragen. Dies dauert etwa 10 Minuten. Nachdem die Kursleitung Gruppen gebildet hat, können Sie" +
    "     unter dem Reiter \"Gruppen\" alle Gruppen sehen. Hier sehen auch andere Teilnehmer Ihre E-Mail. Sollte " +
    "     Ihnen das unangenehm sein, empfehlen wir eine neue Adresse auf web.de oder bei einem ähnlichen " +
    "     Anbieter einzurichten." +
    "       Vielen Dank für Ihre Teilnahme!";

let welcomeTitleDE = "Willkommen";
let welcomeTitleEN = "Welcome";

let buttonLabelDE = "Weiter";
let buttonLabelEN = "next";

let navEN = [{
    groups: "Groups",
    introduction: "Introduction",
    survey: "Profile",
    persist: "Admin",
    logout: "log out"
}];

let navDE = [{
    groups: "Gruppen",
    introduction: "Einleitung",
    survey: "Profil",
    persist: "Admin",
    logout: "ausloggen"
}];

let groupViewLoginEN = [{
    enterEmail: "Please enter your Email here:",
    submit: "submit",
    emailDoesntExist: "This Email does not exists. Please participate in the survey first."
}];

let groupViewLoginDE = [{
    enterEmail: "Bitte geben Sie ihre Email hier ein:",
    submit: "bestätigen",
    emailDoesntExist: "Diese Email existiert nicht. Bitte nehmen Sie an der Umfrage teil."
}];

let computedGroupsEN = [{
    computedGroups: "Computed Groups",
}];

let computedGroupsDE = [{
    computedGroups: "Gruppeneinteilung",
}];

let alreadyParticipatedMessageEN = "You already participated in a survey.";

let alreadyParticipatedMessageDE = "Sie haben bereits an einer Umfrage teilgenommen.";

let mailsDontMatchEN = "The email is not the same as the first one.";

let mailsDontMatchDE = "Diese E-Mail stimmt nicht mit der oberen überein.";


function noGroupsMessageEN(participantsNeeded) {
    let noGroupsEN = [{
        noGroupsYet: "There are no groups built yet.",
        participantsMissing: messageParticipantsNeededEN(participantsNeeded),
        comeBackAfterMail: "Please come back to this page after you get an e-mail that groups have been formed.",
    }];
    return noGroupsEN;
}

function noGroupsMessageDE(participantsNeeded) {
    let noGroupsDE = [{
        noGroupsYet: "Es wurden noch keine Gruppen gebildet.",
        participantsMissing: messageParticipantsNeededDE(participantsNeeded),
        comeBackAfterMail: "Kommen Sie auf diese Seite zurück nachdem sie eine E-Mail bekommen haben.",
    }];
    return noGroupsDE;
}

function messageParticipantsNeededDE(participantsNeeded) {
    return "Es fehlen noch " + participantsNeeded + " Teilnehmer, um Gruppen zu bilden.";
}

function messageParticipantsNeededEN(participantsNeeded) {
    return "There are " + participantsNeeded + " participants missing to create groups.";
}

let surveyJSTitleDE = "Bitte füllen Sie Ihr Profil aus!";
let surveyJSTitleEN = "Please complete your profile!";