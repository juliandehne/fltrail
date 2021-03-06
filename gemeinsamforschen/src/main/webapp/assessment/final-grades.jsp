<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html>

<head>
    <!-- dependencies -->
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Finale Noten</title>
    <script src="js/final-grades.js"></script>
    <link href="css/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="js/datatables.min.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <!-- prints the menu -->
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- go back to tasks -->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> </i>Zurück zu den
            Aufgaben</a>
    </div>
    <!-- go back to tasks end-->

    <main>
        <div class="row group">
            <div class="col span_content span_2_of_2">
                <h3>Abschließende Noten vergeben</h3>

                <div class="row group">
                    <div class="span_2_of_2" style="background:#ededed">
                        <div class="inProgressView">
                            <label>Legende</label>
                            <div id="iconLegend">
                                <div id="colorLegend" class="contributionPeerRating">
                                    <div class="alert-success">
                                        <span>Gleich</span>
                                    </div>
                                    <div class="alert-warning">
                                        <span>Ähnlich<br>(+- 0.3)</span>
                                    </div>
                                    <div class="alert-danger">
                                        <span>Unterschiedlich<br>(+- 0.7)</span>
                                    </div>
                                </div>
                                <div class="workPeerRating">
                                    <p><i class="fas fa-arrow-up"></i> Diese Person wurde von seinen Peers unüblich
                                        gut
                                        bewertet.</p>
                                </div>
                                <div class="workPeerRating">
                                    <p><i class="fas fa-arrow-down"></i> Diese Person wurde von seinen Peers
                                        unüblich
                                        schlecht
                                        bewertet. </p>
                                </div>
                                <div class="workPeerRating">
                                    <p><i class="fas fa-check"></i> Diese Person wurde von seinen Peers ausgeglichen
                                        bewertet.
                                    </p>
                                </div>
                                <p style="font-weight:bold;" class="workPeerRating"> Klicken sie auf das entsprechende
                                    Symbol in der
                                    Tabelle um die
                                    Bewertungen der Studierenden um Ausreißer zu bereinigen.
                                </p>
                            </div>
                            <p>
                                <a id="takeSuggested" style="cursor:pointer; font-size: 15px;"><i
                                        class="fas fa-arrow-right"></i> vorgeschlagene Noten übernehmen</a></p>
                        </div>
                    </div>
                </div>

            </div>


            <div class="alert alert-success" id="taskCompleted">
                Die Noten wurden gespeichert.
            </div>
            <div class="alert alert-warning" id="gradeMissing" hidden>
                Bevor Sie die Noten final speichern können, müssen alle Studierenden eine Note erhalten haben.
                Bitte überprüfen Sie dies.
            </div>

            <div class="table-wrapper-scroll-y my-custom-scrollbar">
                <table id="tableGrades" class="table table-striped table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm" style="width: 25%;">
                            Name
                        </th>
                        <th class="th-sm" style="width: 10%;">
                            Gruppe
                        </th>
                        <th class="th-sm" style="width: 25%;">
                            E-Mail
                        </th>
                        <th class="th-sm contributionPeerRating">
                            (Average)
                            Produkte (Studierende)
                        </th>
                        <th class="th-sm">
                            Produkte (Sie)
                        </th>
                        <th class="th-sm workPeerRating">
                            (Average)
                            Zusammenarbeit
                        </th>
                        <th class="th-sm">
                            (Average)
                            vorgeschlagene Note
                        </th>
                        <th class="th-sm">
                            Endnote
                        </th>
                    </tr>
                    </thead>
                    <tbody id="allGradesOfAllStudents">

                    </tbody>
                </table>
            </div>
            <script id="gradesOfOneStudentTemplate" type="text/x-jQuery-tmpl">
                <tr class="grading" id="grades_${userId}">
                        <td name="name">
                            ${name}
                            <a data-toggle='collapse' href="#${name}" role='button'
                               aria-expanded='false' aria-controls='${name}'>
                                <i class="fas fa-ellipsis-h"></i>
                            </a>
                            <div class='collapse' id="${name}" style='position: absolute;'>
                                <div class='card card-body'>
                                <ul>
                                {{each files}}
                                    <li>
                                    <a href="../rest/fileStorage/download/fileLocation/${filePath}">${fileName}</a>
                                    </li>
                                {{/each}}
                                </ul>
                                </div>
                            </div>
                        </td>
                        <td name="groupName">
                            ${groupName}
                        </td>
                        <td name="userEmail">
                            ${userEmail}
                        </td>
                        <td name="productPeer" class="${levelOfAgreement} contributionPeerRating">
                            ${productPeer}
                        </td>
                        <td name="productDocent" class="${levelOfAgreement}">
                            ${productDocent}
                        </td>
                        <td name="workRating" style="display:flex;" class="workPeerRating">
                            <div class='collapse cleaned'>
                                ${cleanedWorkRating}
                            </div>
                            <div class='collapse in cleaned'>
                                ${workRating}
                            </div>
                            {{if workRating!="fehlt"}}
                            <div class="inProgressView">
                            <a data-toggle='collapse' href='.cleaned' role='button'
                                       aria-expanded='false' aria-controls='cleaned'>
                                  <div class='collapse in cleaned'>
                                      <i class='fas ${beyondStdDeviation}'></i>
                                  </div>
                            </div>
                            <div class='collapse cleaned'>
                                <i class='fas fa-check'></i>
                            </div>
                            </a>
                            {{/if}}
                        </td>
                        <td name="suggested" id="suggested_${userId}">
                            <div class='collapse cleaned'>
                                ${cleanedSuggested}
                            </div>
                            <div class='collapse in cleaned'>
                                ${suggested}
                            </div>
                        </td>
                        <td name="finalMark">
                            <input id="markFor_${userId}" value="${finalMark}" size="4" class="unsavedFinalMark">
                            <p class="savedFinalMark">${finalMark}</p>
                        </td>
                    </tr>
            </script>
            <div class="document-text-buttons inProgressView">
                <div class="document-text-buttons-back">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#exampleModal">
                        <i class="fas fa-arrow-alt-circle-up"></i> veröffentlichen
                    </button>
                </div>
                <button type="button" id="btnSave2" class="btn btn-primary document-text-buttons-next">
                    <i class="far fa-save"></i> speichern
                </button>
                <div>
                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                         aria-labelledby="speichernDialog" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="speichernDialog">Noten final speichern</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    Nach dem Veröffentlichen können Sie die Noten nicht mehr ändern und die Studierenden
                                    können ihre Noten sehen.
                                </div>
                                <div class="modal-footer">
                                    <%--  <button type="button" class="btn btn-secondary" data-dismiss="modal">schließen
                                      </button>--%>
                                    <button type="button" id="btnSave" class="btn btn-warning">veröffentlichen
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Button trigger modal -->
                </div>
            </div>
            <div style="display:flex;">
                <button title="print" id="print" class="btn btn-primary"><i class="fas fa-print"></i> print</button>
            </div>
        </div>
    </main>

    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>


</html>
