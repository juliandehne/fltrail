<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html>

<head>

    <link rel="stylesheet" href="css/eportfolio.css">
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/eportfolio.js"></script>

</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div class="page-content-wrapper">
            <div>
                <table>
                    <tr>
                        <td id="yourContent">

                            <h1>E-Portfolio</h1>

                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="#description">Beschreibung</a></li>
                                <li><a data-toggle="tab" href="#journal-container">Lerntagebuch</a></li>
                            </ul>
                            <div class="tab-content">

                                <div id="description" class="tab-pane fade in active ">
                                    <div class="journal-description-container">
                                        <div class="journal-description-title">
                                        </div>
                                        <div class="journal-description-edit" id="description-edit" align="right">
                                            <a id="editDescriptionLink" class="btn btn-default btn-sm">
                                                <i class="fa fa-pencil-alt"></i> Bearbeiten</a>
                                            <a class="btn btn-default btn-sm" data-toggle="modal"
                                               data-target="#closeDescriptionModal"><i class="fa fa-check-square"
                                                                                       aria-hidden="true"></i>Abschlie&szlig;en</a>
                                            <!-- TODO: Variabel -->
                                            <div class="exportLink">
                                            </div>

                                        </div>
                                        <div class="journal-description-text">
                                        </div>
                                        <div class="journal-description-group">
                                            <h3>Gruppe</h3>
                                        </div>
                                        <div class="journal-description-links">
                                            <h3>Links</h3>

                                        </div>


                                    </div>
                                </div>

                                <div id="journal-container" class="tab-pane fade">
                                    <h2>Lerntagebuch</h2>
                                    <div class="input-group">
                                        <label>
                                            <select id="journalfilter" class="form-control" style="width:auto;"
                                                    onchange="filterJournals()">
                                                <option value="ALL">Alle</option>
                                                <option value="OWN">Eigene</option>
                                            </select>
                                        </label>
                                        <a id="createJournalLink" class="btn btn-default btn-sm">Neu</a>
                                    </div>
                                    <div class="journal">
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="modal fade" id="addLinkModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Link hinzuf&uuml;gen</h4>
                    </div>
                    <div class="modal-body">
                        <form id="linkform" method="POST" action="rest/projectdescription/addLink">
                            <input type="hidden" name="projectdescriptionId" id="projectdescriptionId" value=""/>
                            <label>Name:<br>
                                <input type="text" name="name" form="linkform">
                            </label>
                            <br>
                            <label>URL:<br>
                                <input type="url" name="link" form="linkform">
                            </label>
                            <br><br>
                            <input class="btn btn-default" type="submit">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Abbrechen</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="closeJournalModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Tagebucheintrag schließen</h4>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="journalID" id="journalID-input" value=""/>
                        Tagebucheintrag schließen? Dieser Eintrag kann nach Bestätigung nicht mehr bearbeitet werden.
                    </div>
                    <div class="modal-footer">
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary mr-auto" data-dismiss="modal"
                                    onclick="closeJournal()">
                                Ja
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Nein</button>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="closeDescriptionModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Beschreibung schließen</h4>
                    </div>
                    <div class="modal-body">
                        Beschreibung schließen? Die Projektbeschreibung kann nach Bestätigung nicht mehr bearbeitet
                        werden.
                    </div>
                    <div class="modal-footer">
                        <div class="btn-group">
                            <button type="button" class="btn btn-primary mr-auto" data-dismiss="modal"
                                    onclick="closeDescription()">Ja
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Nein</button>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col span_chat">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>