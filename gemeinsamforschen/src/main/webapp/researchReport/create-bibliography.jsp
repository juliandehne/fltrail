<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu"%>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<!DOCTYPE html>


<!-- Tipp @Johannes_: du kannst eine jsp verwenden anstatt 10, die immer wieder auf sich selber verweist und dabei
den Zustand ändert
-->

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
        <h1> Forschungsbericht erstellen 3/8</h1>
    </div>

    <div class ="researchReportEditor">
        <h2 class="editor-inhalt">Literaturverzeichnis eingeben:</h2>
        <textarea id = "editor" name="text" form="researchReportForm" rows="20" cols="100">
				</textarea>
    </div>
    <div class="ResearchReportButtons">
        <button class="researchReportButtons"><a href="create-question.jsp">Speichern & weiter</a></button>
        <button class="researchReportButtons"><a href="create-research.jsp"> Zur&uuml;ck </a></button>
    </div>
    <div class="ResearchReportUpload">
        <input type="file" class="filepond" name="filepond"> </input>
    </div>

    <div class="researchReportProgress">
        <nav>
            <menu>
                <menuitem><a id="title">Titel</a> </menuitem>
                <menuitem><a id="recherche">Recherche</a> </menuitem>
                <menuitem><a id="bibo"><font color="#green">Literaturverzeichnis</font></a> </menuitem>
                <menuitem><a id="question">Forschnugsfrage</a> </menuitem>
                <menuitem><a id="concept">Konzept</a></menuitem>
                <menuitem><a id="method">Methodik</a></menuitem>
                <menuitem><a id="reportDo">Durchf&uuml;hrung</a></menuitem>
                <menuitem><a id="evaluation">Evalution</a></menuitem>
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


<script  src="../assets/js/createReportBibo.js"></script>
<script  src="../assets/js/createReportProgress.js"></script>
<script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->
<script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script> <!--FilePond -->
<script>FilePond.parse(document.body);</script> <!--FilePond -->

</body>
</html>