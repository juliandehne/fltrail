

<!DOCTYPE html>
<html>

<head>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/create-journal.css">
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Journal erstellen</title>
    <script src="js/create-journal.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>

    <jsp:include page="../taglibs/jsp/timeLine.jsp"/>
    <div class="col span_content">
        <div class="page-content-wrapper">
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
                                            <option value="DOZENT"> Dozierende Person</option>
                                            <option value="NONE"> Nur Ich</option>
                                        </select>
                                    </div>

                                    <div class="journal-form-category">
                                        Kategorie:
                                        <select name="category" form="journalform">
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
    <div class="col span_chat"><chat:chatWindow orientation="right" scope="project"/> <chat:chatWindow
            orientation="right" scope="group"/></div>
</main>
    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>