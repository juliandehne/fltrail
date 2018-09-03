<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>

<html>
<head>
    <omniDependencies:omniDependencies/>
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css" rel="stylesheet"> <!--FilePond -->
    <link rel="stylesheet" type="text/css" href="../assets/css/researchReport.css">
    <title>Forschungsbericht erstellen</title>
</head>
<body>




<div id="wrapper">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div style="margin-left:50px;">
            <table>
                <tr>
                    <td  id="yourContent">
                        <form id="researchReportform" class="researchReportForm" method="POST" action="../rest/researchReport/save">


    <div class = "researchReportTitlebar">
        <h1> Forschungsbericht erstellen 4/8</h1>
    </div>

    <div class ="researchReportEditor">
        <h2 class="editor-inhalt">Forschungsfrage eingeben:</h2>
        <textarea id = "editor" name="text" form="researchReportForm" rows="10" cols="100">
				</textarea>
    </div>
    <div class="ResearchReportButtons">
        <button class="researchReportButtons"><a href="researchReportConcept.jsp">Speichern & weiter</a></button>
        <button class="researchReportButtons"><a href="researchReportBibo.jsp"> Zur&uuml;ck </a></button>
    </div>
    <div class="ResearchReportUpload">
        <input type="file" class="filepond" name="filepond"> </input>
    </div>
    <div class="researchReportProgress">
        <nav>
            <menu>
                <menuitem><a href="researchReportTitle.jsp">Titel</a> </menuitem>
                <menuitem><a href="researchReportRecherche.jsp">Recherche</a> </menuitem>
                <menuitem><a href="researchReportBibo.jsp">Literaturverzeichnis</a> </menuitem>
                <menuitem><a href="researchReportQuestion.jsp"><font color="#green">Forschnugsfrage</font></a> </menuitem>
                <menuitem><a href="researchReportConcept.jsp">Konzept</a></menuitem>
                <menuitem><a href="researchReportMethod.jsp">Methodik</a></menuitem>
                <menuitem><a href="researchReportDo.jsp">Durchf&uuml;hrung</a></menuitem>
                <menuitem><a href="researchReportEvaluation.jsp">Evalution</a></menuitem>
            </menu>
        </nav>
    </div>

                        </form>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>


<script src="../assets/js/createReport.js"></script>
<script src="../assets/js/researchReportUpload.js"></script>
<script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->
<script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script> <!--FilePond -->
<script>FilePond.parse(document.body);</script> <!--FilePond -->

</body>
</html>