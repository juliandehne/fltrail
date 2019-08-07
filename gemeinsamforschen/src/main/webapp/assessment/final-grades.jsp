<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html>

<head>
    <!-- dependencies -->
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/final-grades.js"></script>
    <link href="css/datatables.min.css" rel="stylesheet">
    <script type="text/javascript" src="js/datatables.min.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <!-- prints the menu -->
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- go back to tasks -->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>
    <!-- go back to tasks end-->

    <main>


        <div class="row group">
            <div class="col span_content span_2_of_2">
                <h3>Abschließende Noten vergeben</h3>

                <div class="row group">
                    <div class="span_2_of_2" style="background:#ededed">

                        <label>Legende</label>
                        <div id="iconLegend">
                            <div id="colorLegend">
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
                            <div>
                                <p><i class="fas fa-arrow-up"></i> Dieser Student wurde von seinen Peers unüblich gut
                                    bewertet.</p>
                            </div>
                            <div>
                                <p><i class="fas fa-arrow-down"></i> Dieser Student wurde von seinen Peers unüblich
                                    schlecht
                                    bewertet. </p>
                            </div>
                            <div>
                                <p><i class="fas fa-check"></i> Dieser Student wurde von seinen Peers ausgeglichen
                                    bewertet.
                                </p>
                            </div>
                        </div>
                        <p style="font-weight:bold;"> Klicken sie auf das entsprechende Symbol in der Tabelle um die
                            Bewertungen der Studierenden
                            um Ausreißer zu bereinigen.
                        </p>
                    </div>
                </div>

            </div>


            <div class="alert alert-success" id="taskCompleted">
                Die Noten wurden gespeichert.
            </div>
            <div class="alert alert-warning" id="gradeMissing" hidden>
                Bevor Sie die Noten final speichern können, müssen alle Studenten eine Note erhalten haben.
                Bitte überprüfen Sie dies.
            </div>

            <div class="table-wrapper-scroll-y my-custom-scrollbar">
                <table id="tableGrades" class="table table-striped table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm">
                            Name
                        </th>
                        <th class="th-sm">
                            Gruppe
                        </th>
                        <th class="th-sm">
                            E-Mail
                        </th>
                        <th class="th-sm">
                            (Average)
                            Produkte (Studierende)
                        </th>
                        <th class="th-sm">
                            Produkte (Sie)
                        </th>
                        <th class="th-sm">
                            (Average)
                            Gruppenarbeit
                        </th>
                        <th class="th-sm">
                            (Average)
                            vorgeschlagene Note
                        </th>
                        <th><a id="takeSuggested" style="cursor:pointer; font-size: 15px;"><i
                                class="fas fa-arrow-right"></i></a>
                            übernehmen
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
                        <td name="groupId">
                            ${groupId}
                        </td>
                        <td name="userEmail">
                            ${userEmail}
                        </td>
                        <td name="productPeer" class="${levelOfAgreement}">
                            ${productPeer}
                        </td>
                        <td name="productDocent" class="${levelOfAgreement}">
                            ${productDocent}
                        </td>
                        <td name="workRating" style="display:flex;">
                            <div class='collapse cleaned'>
                                ${cleanedWorkRating}
                            </div>
                            <div class='collapse in cleaned'>
                                ${workRating}
                            </div>
                            <a data-toggle='collapse' href='.cleaned' role='button'
                                       aria-expanded='false' aria-controls='cleaned'>
                                       <div class='collapse in cleaned'>
                                <i class='fas ${beyondStdDeviation}'></i>
                            </div>
                            <div class='collapse cleaned'>
                                <i class='fas fa-check'></i>
                            </div>

                            </a>
                        </td>
                        <td name="suggested" id="suggested_${userId}">
                            <div class='collapse cleaned'>
                                ${cleanedSuggested}
                            </div>
                            <div class='collapse in cleaned'>
                                ${suggested}
                            </div>
                        </td>
                        <td></td>
                        <td name="finalMark">
                            <input id="markFor_${userId}" value="${finalMark}" size="4" class="unsavedFinalMark">
                            <p class="savedFinalMark">${finalMark}</p>
                        </td>
                    </tr>



            </script>
            <div style="display:flex;" id="divForSaving">
                <button id="btnSave" type="button" class="btn btn-primary" title="weiter">
                    <i class="far fa-save"></i> speichern
                </button>
                <label for="finalizeGrading">
                    Dies ist die finale Benotung der Studierenden
                    <input id="finalizeGrading" style="margin-top:6px;" type="checkbox" title="finalisieren">
                </label>
                <div style="margin-left:65%;">
                    <button title="print" id="print" class="btn btn-primary"><i class="fas fa-print"></i> print</button>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="../taglibs/footer.jsp"/>
</body>


</html>
