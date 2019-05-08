<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" , charset="utf-8">
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css"
          rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css"
          rel="stylesheet"> <!--FilePond -->
    <link rel="stylesheet" type="text/css" href="css/researchReportOverview.css">
    <script src="js/createReportOverview.js"></script>
    <title>Forschungsbericht Übersicht</title>


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
                                <form id="researchReportOverview" class="researchReportOverview">
                                    <div class="researchReportTitlebar">
                                        <h1> Forschungsbericht Übersicht</h1>
                                    </div>


                                    <div class="reports">
                                        <h2>Forschungberichte</h2>
                                        <table>
                                            <thead>
                                            <tr>
                                                <th>Titel</th>
                                                <th>Autor</th>
                                                <th>Placeholder</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>Titel1</td>
                                                <td>Autor1</td>
                                                <td>1</td>
                                            </tr>
                                            <tr>
                                                <td>Titel2</td>
                                                <td>Autor2</td>
                                                <td>2</td>
                                            </tr>
                                            <tr>
                                                <td>Titel3</td>
                                                <td>Autor3</td>
                                                <td>3</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                    <!--  <div class="ResearchReportUpload">
                                          <h3>Gesamten Forschungsbericht hochladen </h3>
                                          <input type="file" class="filepond" name="filepond">
                                      </div> -->

                                    <div class="feedbacks">
                                        <h2>Feedbacks</h2>
                                        <table>
                                            <thead>
                                            <tr>
                                                <th>Titel</th>
                                                <th>Autor</th>
                                                <th>Placeholder</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>Titel1</td>
                                                <td>Autor1</td>
                                                <td>1</td>
                                            </tr>
                                            <tr>
                                                <td>Titel2</td>
                                                <td>Autor2</td>
                                                <td>2</td>
                                            </tr>
                                            <tr>
                                                <td>Titel3</td>
                                                <td>Autor3</td>
                                                <td>3</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>


                                    <div class="ResearchReportButtons">
                                        <button class="researchReportButtons"><a id="uploader">Upload File</a></button>
                                        <button class="researchReportButtons"><a id="forwardLink">Bericht erstellen</a>
                                        </button>
                                        <button class="researchReportButtons"><a id="backLink">Zur&uuml;ck</a></button>
                                    </div>

                                </form>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>


        <script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->
        <script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script>
        <!--FilePond -->
        <script>FilePond.parse(document.body);</script> <!--FilePond -->


    </div>
</main>
</div>
</body>
</html>
