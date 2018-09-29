<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/create-journal.css">
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/create-journal.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId">project1
                <a href="#">
                <span class="glyphicon glyphicon-envelope"
                      style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
                </a>
                <a href="#">
                    <span class="glyphicon glyphicon-cog"
                          style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
                </a></h1>
        </div>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1> Tagebucheintrag erstellen </h1>

                        <form id="journalform" class="form-journal" method="POST" action="../rest/journal/save">

                            <input type="hidden" id="student" name="student">
                            <input type="hidden" id="project" name="project">
                            <input type="hidden" id="journalid" name="id">

                            <div class="journal-form-container">

                                <div class="journal-form-visibility">
                                    Sichtbarkeit:
                                    <select id="visibility" name="visibility" form="journalform">
                                        <option value="ALL"> Alle</option>
                                        <option value="GROUP"> Gruppe</option>
                                        <option value="DOZENT"> Dozent</option>
                                        <option value="MINE"> Nur Ich</option>
                                    </select>
                                </div>

                                <div class="journal-form-category">
                                    Kategorie:
                                    <select id="category" name="category" form="journalform">
                                        <option value="TITEL"> Titel</option>
                                        <option value="RECHERCHE"> Recherche</option>
                                        <option value="LITERATURVERZEICHNIS"> Literaturverzeichnis</option>
                                        <option value="FORSCHUNGSFRAGE"> Forschungsfrage</option>
                                        <option value="UNTERSUCHUNGSKONZEPT"> Untersuchungskonzept</option>
                                        <option value="METHODIK"> Methodik</option>
                                        <option value="DURCHFUEHRUNG"> Durchführung</option>
                                        <option value="AUSWERTUNG"> Auswertung</option>

                                    </select>
                                </div>


                                <div class="journal-form-editor">
                                    <textarea id="editor" name="text" form="journalform"></textarea>
                                </div>

                                <div class="journal-form-buttons">
                                    <input class="btn btn-default btn-sm" type="submit">
                                    <a id="backLink" class="btn btn-default btn-sm"> Zur&uuml;ck </a>
                                </div>

                            </div>
                        </form>

                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>

</html>