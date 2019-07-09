<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>fltrailwizard</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <script type="application/javascript" src="assets/js/jquery.min.js"></script>
    <script type="application/javascript" src="../taglibs/js/utility.js"></script>
    <script type="application/javascript" src="js/wizard.js"></script>

</head>

<body>
<div class="row">
    <div class="col">
        <h1>Projektauswahl</h1>
        <div class="dropdown"><button class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                                      aria-expanded="false"
                                      type="button"><span id="projectButtonText">Projekt auswählen</span></button>
            <div class="dropdown-menu" role="menu" id="dropdownContainer">
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col">
        <h1>Gruppenfindungsphase</h1>
        <div class="row">
            <div class="col" style="margin-top: 10px;"><button disabled id="createStudents" class="btn btn-primary"
                                                               type="button"
                                                               style="width:
            200px;">Studenten anlegen</button><span class="text-left" style="margin: 20px;">30 Studenten generieren und
                anmelden lassen</span>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button" style="width: 200px;">überspringen</button><span style="margin: 20px;">Gruppenfindungsphase überspringen</span></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col">
        <h1>Konzept entwickeln</h1>
        <div class="row">
            <div class="col"><button disabled class="btn btn-primary" type="button" style="width: 200px;">Dossiers
                anlegen</button><span style="margin: 20px;">Initiales Dossier für jede Gruppe generieren und anlegen</span>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button" style="width: 200px;">Dossiers annotieren</button><span style="margin: 20px;">Dossiers annotieren lassen</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Feedbacks
                        generieren</button><span style="margin: 20px;">Alle Gruppen geben Feedback</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Dossiers
                        finalisieren</button><span style="margin: 20px;">Alle Gruppen reeditieren und
                        finalisieren Dossier</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Phase
                        überspringen</button><span style="margin: 20px;">Phase überspringen</span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col">
        <h1>Reflexionsphase</h1>
        <div class="row">
            <div class="col"><button disabled class="btn btn-primary" type="button" style="width: 200px;">Phase
                überspringen</button><span
                    style="margin: 20px;">Die Reflexionsphase überspringen</span>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col">
        <h1>Peer Assessment</h1>
        <div class="row">
            <div class="col"><button disabled class="btn btn-primary" type="button" style="width: 200px;">Präsentationen</button>
                <span style="margin: 20px;">Präsentationen für alle Gruppen generieren und hochladen</span>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Abschlussberichte
                    </button><span style="margin: 20px;">Abschlussbericht für alle Gruppen generieren und hochladen</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Externe
                        PAs generieren</button><span style="margin: 20px;">Es werden Bewertungen für
                        andere Gruppen generiert</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Interne PAs
                        generieren
                        </button><span style="margin: 20px;">Es werden für alle Gruppen interne Bewertungen
                        für die eigene Arbeit generiert</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Dozenten PAs
                        generieren
                    </button><span style="margin: 20px;">Es werden für alle Gruppen Bewertungen des Dozenten
                        generiert</span></div>
                </div>
                <div class="row">
                    <div class="col" style="margin-top: 10px;"><button disabled class="btn btn-primary" type="button"
                                                                       style="width: 200px;">Phase
                        überspringen</button><span style="margin: 20px;">Phase überspringen</span></div>
                </div>
            </div>
        </div>
    </div>
</div>





<div class="row"></div>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>