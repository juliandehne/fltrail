<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>

<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css"
          rel="stylesheet"> <!--FilePond -->
    <link rel="stylesheet" type="text/css" href="css/researchReport.css">
    <title>Forschungsbericht erstellen</title>
</head>
<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main> <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div id="wrapper">
            <div class="page-content-wrapper">
                <div style="margin-left:50px;">
                    <table>
                        <tr>
                            <td id="yourContent">
                                <form id="researchReportform" class="researchReportForm" method="POST"
                                      action="../rest/researchReport/save">

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

                                    <div class="researchReportTitlebar">
                                        <h1> Forschungsbericht erstellen 1/8</h1>
                                    </div>

                                    <div class="researchReportEditor">
                                        <h2 class="editor-inhalt">Titel eingeben:</h2>
                                        <textarea id="editor" name="text" form="researchReportForm" rows="20"
                                                  cols="100">
				</textarea>
                                    </div>
                                    <div class="ResearchReportButtons">
                                        <button class="researchReportButtons"><a id="forwardLink">Speichern & weiter</a>
                                        </button>
                                        <button class="researchReportButtons"><a id="backLink"> Zur&uuml;ck </a>
                                        </button>
                                    </div>

                                    <div class="ResearchReportUpload">
                                        <input type="file" class="filepond" name="filepond"> </input>
                                    </div>


                                    <div class="researchReportProgress">
                                        <nav>
                                            <menu>
                                                <menuitem><a id="title"><font color="#green">Titel</font></a></menuitem>
                                                <menuitem><a id="recherche">Recherche</a></menuitem>
                                                <menuitem><a id="bibo">Literaturverzeichnis</a></menuitem>
                                                <menuitem><a id="question">Forschnugsfrage</a></menuitem>
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


        <script src="js/createReportTitle.js"></script>
        <script src="js/createReportProgress.js"></script>
        <script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->
        <script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script>
        <!--FilePond -->
        <script>FilePond.parse(document.body);</script> <!--FilePond -->

    </div>
</main>
</div>
</body>
</html>