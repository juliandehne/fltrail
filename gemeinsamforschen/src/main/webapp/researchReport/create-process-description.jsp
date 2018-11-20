<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<!DOCTYPE html>

<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css"
          rel="stylesheet"> <!--FilePond -->
    <link rel="stylesheet" type="text/css" href="css/researchReport.css">
    <title>Forschungsbericht erstellen</title>
</head>
<body>
<menu:menu hierarchy="1"/><div class="col span_content">
<div id="wrapper">
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div style="margin-left:50px;">
            <table>
                <tr>
                    <td id="yourContent">
                        <form id="researchReportform" class="researchReportForm" method="POST"
                              action="../rest/researchReport/save">

                            <div class="researchReportTitlebar">
                                <h1> Forschungsbericht erstellen 7/8</h1>
                            </div>

                            <div class="researchReportEditor">
                                <h2 class="editor-inhalt">Durchf&uuml;hrung eingeben:</h2>
                                <textarea id="editor" name="text" form="researchReportForm" rows="20" cols="100">
				</textarea>
                            </div>
                            <div class="ResearchReportButtons">
                                <button class="researchReportButtons"><a href="create-evaluation.jsp">Speichern &
                                    weiter</a></button>
                                <button class="researchReportButtons"><a href="create-method.jsp"> Zur&uuml;ck </a>
                                </button>
                            </div>
                            <div class="ResearchReportUpload">
                                <input type="file" class="filepond" name="filepond"> </input>
                            </div>

                            <div class="researchReportProgress">
                                <nav>
                                    <menu>
                                        <menuitem><a id="title">Titel</a></menuitem>
                                        <menuitem><a id="recherche">Recherche</a></menuitem>
                                        <menuitem><a id="bibo">Literaturverzeichnis</a></menuitem>
                                        <menuitem><a id="question">Forschnugsfrage</a></menuitem>
                                        <menuitem><a id="concept">Konzept</a></menuitem>
                                        <menuitem><a id="method"><font color="#green">Methodik</font></a></menuitem>
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


<script src="js/createReportDo.js"></script>
<script src="js/createReportProgress.js"></script>
<script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->
<script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script>
<!--FilePond -->
<script>FilePond.parse(document.body);</script> <!--FilePond -->

</body>
</html>