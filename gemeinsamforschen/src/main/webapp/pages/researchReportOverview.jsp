<%--
  Created by IntelliJ IDEA.
  User: quark
  Date: 18.07.2018
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1", charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link href="https://unpkg.com/filepond/dist/filepond.css" rel="stylesheet"> <!--FilePond -->
    <link href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css" rel="stylesheet"> <!--FilePond -->

    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../assets/js/utility.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/researchReportOverview.css">
    <title>Forschungsbericht Übersicht</title>


</head>

<body>


<form id="researchReportOverview" class="researchReportOverview">
    <div class = "researchReportTitlebar">
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

    <div class="ResearchReportUpload">
        <h3>Gesamten Forschungsbericht hochladen </h3>
        <input type="file" class="filepond" name="filepond">
    </div>

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

</form>




<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
<script src="https://unpkg.com/filepond/dist/filepond.js"></script> <!--FilePond -->
<script src="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.js"></script> <!--FilePond -->
<script src="../assets/js/researchReportUpload.js"></script><!--FilePond -->
<script>FilePond.parse(document.body);</script> <!--FilePond -->


</body>
</html>
