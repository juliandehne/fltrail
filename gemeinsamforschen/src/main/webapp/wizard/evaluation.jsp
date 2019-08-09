<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>fltrailwizard</title>
    <script type="application/javascript" src="assets/js/jquery.min.js"></script>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="../libs/css/styles.css">
    <script src="../taglibs/js/utility.js"></script>

    <script type="application/javascript" src="../taglibs/js/enum.js"></script>
    <script type="application/javascript" src="js/wizard.js"></script>

</head>

<body>
<div class="loader-inactive" id="loader">
    <div class="sk-cube1 sk-cube"></div>
    <div class="sk-cube2 sk-cube"></div>
    <div class="sk-cube4 sk-cube"></div>
    <div class="sk-cube3 sk-cube"></div>
</div>
<div class="row">
    <div class="col">
        <h1>Projektauswahl</h1>
        <div class="dropdown">
            <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                    aria-expanded="false">
                <span id="projectButtonText">Projekt auswählen</span>
            </button>
            <div class="dropdown-menu" role="menu" id="dropdownContainer">
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col" id="projectPhases">
        <h1>Gruppenfindungsphase</h1>
        <div class="row">
            <div class="col" style="margin-top: 10px;">
                <button disabled id="createStudents"
                        class="btn btn-primary groupfindingButton btn-wizard">
                    Studenten anlegen
                </button>
                <span class="text-left" style="margin: 20px;">30 Studenten generieren und
                anmelden lassen</span>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button id="skipGroupPhase" disabled
                                class="btn btn-primary groupfindingButton btn-wizard">
                            Phase überspringen
                        </button>
                        <span style="margin: 20px;">Gruppenfindungsphase überspringen</span></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="testStudentDisplay"></div>
<div class="row">
    <div class="col">
        <h1>Konzept entwickeln</h1>
        <div class="row">
            <div class="col">
                <button id="uploadDossierButton" disabled class="btn btn-primary dossierButton btn-wizard">
                    Dossiers
                    anlegen
                </button>
                <span style="margin: 20px;">Initiales Dossier für jede Gruppe generieren und anlegen</span>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button id="annotateDossierButton" disabled
                                class="btn btn-primary dossierButton btn-wizard">
                            Dossiers annotieren
                        </button>
                        <span style="margin: 20px;">Dossiers annotieren lassen</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button id="giveFeedbackButton" disabled
                                class="btn btn-primary dossierButton btn-wizard">
                            Feedbacks generieren
                        </button>
                        <span style="margin: 20px;">Alle Gruppen geben Feedback</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button disabled id="finalizeDossierButton"
                                class="btn btn-primary dossierButton btn-wizard">
                            Dossiers finalisieren
                        </button>
                        <span style="margin: 20px;">Alle Gruppen reeditieren und
                        finalisieren Dossier</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button id="skipDossierPhase" disabled
                                class="btn btn-primary dossierButton btn-wizard">
                            Phase überspringen
                        </button>
                        <span style="margin: 20px;">Phase überspringen</span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col">
        <h1>Reflexionsphase</h1>

        <div class="row">
            <div class="col">
                <button disabled id="selectQuestionsForProject" class="btn btn-primary reflexionButton btn-wizard">
                    Fragen auswählen
                </button>
                <span style="margin: 20px;">Die Reflexionsfragen, die die Studierenden beantworten sollen, werden
                ausgewählt</span>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <button disabled id="writeEPortfolioEntries" class="btn btn-primary reflexionButton btn-wizard">
                    Einträge schreiben
                </button>
                <span style="margin: 20px;">E-Portfolio-Einträge für alle Studierende schreiben (persönlich und Gruppe)</span>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <button disabled id="giveFeedbackForReflexion" class="btn btn-primary reflexionButton btn-wizard">
                    Feedback generieren
                </button>
                <span style="margin: 20px;">Dozent gibt Feedback zu antworten von Reflexionsfragen</span>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <button disabled id="selectEntriesForAssessment" class="btn btn-primary reflexionButton btn-wizard">
                    Auswahl treffen
                </button>
                <span style="margin: 20px;">die Auswahl der Studierende der Einträge für das Assessment wird
                generiert</span>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <button disabled id="answerQuestionsForProject" class="btn btn-primary reflexionButton btn-wizard">
                    Fragen beantworten
                </button>
                <span style="margin: 20px;">Für die Reflexionsfragen, die die Studierenden beantworten sollen, werden
                Antworten generiert</span>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <button disabled id="skipExecutionPhase" class="btn btn-primary reflexionButton btn-wizard">
                    Phase überspringen
                </button>
                <span style="margin: 20px;">Die Reflexionsphase überspringen</span>
            </div>
        </div>


    </div>
</div>

<div class="row">
    <div class="col">
        <h1>Peer Assessment</h1>
        <div class="row">
            <div class="col">
                <button disabled id="uploadPresentationButton" class="btn btn-primary assessmentButton btn-wizard">
                    Präsentationen
                </button>
                <span style="margin: 20px;">Präsentationen für alle Gruppen generieren und hochladen</span>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button disabled id="uploadFinalReportButton"
                                class="btn btn-primary assessmentButton btn-wizard">
                            Abschlussberichte
                        </button>
                        <span style="margin: 20px;">Abschlussbericht für alle Gruppen generieren und hochladen</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button disabled id="externalPAButton"
                                class="btn btn-primary GRADING btn-wizard">
                            Externe PAs generieren
                        </button>
                        <span style="margin: 20px;">Es werden Bewertungen für
                        andere Gruppen generiert</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button disabled id="internalPAButton"
                                class="btn btn-primary GRADING btn-wizard">
                            Interne PAs generieren
                        </button>
                        <span style="margin: 20px;">Es werden für alle Gruppen interne Bewertungen
                        für die eigene Arbeit generiert</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button disabled id="docentPAButton"
                                class="btn btn-primary Projectfinished btn-wizard">
                            Dozenten PAs generieren
                        </button>
                        <span style="margin: 20px;">Es werden für alle Gruppen Bewertungen des Dozenten
                        generiert</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;">
                        <button disabled id="finishAssessmentAndGradingButton"
                                class="btn btn-primary Projectfinished btn-wizard">
                            Phase überspringen
                        </button>
                        <span style="margin: 20px;">Phase überspringen</span></div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="row"></div>
</body>

</html>