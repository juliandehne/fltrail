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
            <div class="col span_content span_l_of_2">
                <h2>Abschließende Noten vergeben</h2>
                <div class="alert alert-success" id="taskCompleted">
                    Die Zensuren wurden gespeichert.
                </div>
                <div class="table-wrapper-scroll-y my-custom-scrollbar">
                    <table id="tableGrades" class="table table-striped table-sm" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th class="th-sm">
                                Name
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
                            <td><a id="takeSuggested" style="cursor:pointer; font-size: 15px;"><i
                                    class="fas fa-arrow-right"></i></a></td>
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
                            ${workRating}
                            <i class="fas ${beyondStdDeviation}"></i>
                        </td>
                        <td name="suggested" id="suggested_${userId}">
                            ${suggested}
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
                    <label class="checkbox" for="finalizeGrading">
                        Dies ist die finale Benotung der Studierenden
                        <input id="finalizeGrading" style="margin:2px 0 0 0" type="checkbox" title="finalisieren">

                    </label>
                </div>
            </div>
            <div class="col span_content span_s_of_2" style="margin-top: 15%;">
                <h4>Legende</h4>
                <div style="display: block;">
                    <div style="display: flex; margin-bottom: 5px;">
                        <i class="fas fa-arrow-up"></i> Dieser Student wurde von seinen Peers unüblich gut bewertet.
                    </div>
                    <div style="display: flex; margin-bottom: 5px;">
                        <i class="fas fa-arrow-down"></i> Dieser Student wurde von seinen Peers unüblich schlecht
                        bewertet.
                    </div>
                    <div style="display: flex; margin-bottom: 5px;">
                        <i class="fas fa-check"></i> Dieser Student wurde von seinen Peers ausgeglichen bewertet.
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="../taglibs/footer.jsp"/>
</body>


</html>
