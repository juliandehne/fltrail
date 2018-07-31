<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tagebucheintrag erstellen</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="../assets/js/utility.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/editDescription.css">

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
                        <h1> Projektbeschreibung bearbeiten </h1>

                        <form id="descriptionform" class="form-journal" method="POST" action="../rest/projectdescription/saveText">

                            <input type="hidden" name="student" value="0">
                            <input type="hidden" name="project" value="0">

                            <div class="description-form-container">

                                <div class ="description-form-editor">
                                    <textarea id = "editor" name="text" form="descriptionform"></textarea>
                                </div>

                                <div class="description-form-buttons">
                                    <input class="btn btn-default btn-sm" type="submit">
                                    <a id="backLink" class="btn btn-default btn-sm">Zur&uuml;ck</a>
                                </div>

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
<script  src="../assets/js/editDescription.js"></script>
</body>

</html>