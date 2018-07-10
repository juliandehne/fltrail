<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->

    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../assets/js/utility.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/researchReport.css">
    <title>Forschungsbericht erstellen</title>
</head>
<body>

<div id="wrapper">
    <menu:menu></menu:menu>
        <div class="page-content-wrapper">
            <div class="container-fluid">
                <h1 id="projectId">project1
                    <a href="#">
                <span class="glyphicon glyphicon-envelope"
                      style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
                    </a>
                    <a href="#">
                        <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
                    </a></h1>
            </div>

            <div>
                <table>
                    <tr>
                        <td  id="yourContent">
                            <form id="researchReportform" class="researchReportForm" method="POST" action="../rest/researchReport/save">

 <!-- <div class = "researchReportMenu">
        <nav>
            <menu>
                <menuitem><p><a href="">Gruppe</a></p> </menuitem>
                <menuitem><p><a href="">Projekt erstellen</a> </p></menuitem>
                <menuitem><p><a href="">Forschungsbericht erstellen</a></p></menuitem>
                <menuitem><p><a href="">Review</a></p></menuitem>
                <menuitem><p><a href="">User</a></p></menuitem>
            </menu>
        </nav>
    </div> -->

    <div class = "researchReportTitlebar">
        <h1> Forschungsbericht erstellen 1/8</h1>
    </div>

    <div class ="researchReportEditor">
        <h2 class="editor-inhalt">Titel eingeben:</h2>
        <textarea id = "editor" name="text" form="researchReportForm" rows="20" cols="100">
				</textarea>
    </div>
    <div class="ResearchReportButtons">
        <button class="researchReportButtons"><a href="researchReportRecherche.jsp">Speichern & weiter</a></button>
        <button class="researchReportButtons"><a href="project-student.jsp"> Zur&uuml;ck </a></button>
    </div>

    <div class="ResearchReportUpload">
        <input type="file" class="filepond" name="filepond"> </input>
    </div>

    <div class="researchReportProgress">
        <nav>
            <menu>
                <menuitem><a href="researchReportTitle.jsp"><font color="#green">Titel</font></a> </menuitem>
                <menuitem><a href="researchReportRecherche.jsp">Recherche</a> </menuitem>
                <menuitem><a href="researchReportBibo.jsp">Literaturverzeichnis</a> </menuitem>
                <menuitem><a href="researchReportQuestion.jsp">Forschnugsfrage</a> </menuitem>
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

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
<script  src="../assets/js/createReport.js"></script>


</body>
</html>