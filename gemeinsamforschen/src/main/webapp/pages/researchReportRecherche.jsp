<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
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



<form id="researchReportform" class="researchReportForm" method="POST" action="../rest/researchReport/save">

    <div class = "researchReportMenu">
        <nav>
            <menu>
                <menuitem><p><a href="">Gruppe</a></p> </menuitem>
                <menuitem><p><a href="">Projekt erstellen</a> </p></menuitem>
                <menuitem><p><a href="">Forschungsbericht erstellen</a></p></menuitem>
                <menuitem><p><a href="">Review</a></p></menuitem>
                <menuitem><p><a href="">User</a></p></menuitem>
            </menu>
        </nav>
    </div>

    <div class = "researchReportTitlebar">
        <h1> Forschungsbericht erstellen 2/8</h1>
    </div>

    <div class ="researchReportEditor">
        <h2 class="editor-inhalt">Recherche eingeben:</h2>
        <textarea id = "editor" name="text" form="researchReportForm" rows="20" cols="100">
				</textarea>
    </div>
    <div class="ResearchReportButtons">
        <button class="researchReportButtons"><a href="researchReportBibo.jsp">Speichern & weiter</a></button>
        <button class="researchReportButtons"><a href="researchReportTitle.jsp"> Zur&uuml;ck </a></button>
    </div>
    <div class="researchReportProgress">
        <nav>
            <menu>
                <menuitem><a href="researchReportTitle.jsp">Titel</a> </menuitem>
                <menuitem><a href="researchReportRecherche.jsp"><font color="#green">Recherche</font></a> </menuitem>
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

<script>

</script>

</body>
</html>