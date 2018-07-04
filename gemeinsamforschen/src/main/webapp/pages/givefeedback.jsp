<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>muster-gemeinsam-forschen</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://cdn.rawgit.com/showdownjs/showdown/1.8.5/dist/showdown.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/project-student.js"></script>
    <link rel="stylesheet" type="text/css" href="../assets/css/editDescription.css">
    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>
    <script  src="../assets/js/editDescription.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId"> PeerFeedback</h1>
        </div>
        <div align="right" class="dropdown" >
            <button style= "position: absolute; right: 50px;" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">

                <i class="glyphicon glyphicon-envelope"></i>
            </button>

            <ul class="dropdown-menu">
                <li><a class="viewfeedback" role="button">Feedback A</a></li>
                <li><a class="viewfeedback" role="button">Feedback B</a></li>
                <li><a class="viewfeedback" role="button">Feedback C</a></li>
            </ul>

            <a href="#">
                <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-right:30px;margin-top:3px;"></span>
            </a>

        </div>

        <div>

        </div>
        <div>
            <table>
                <tr>
                    <h2> Gib dein Feedback ein!</h2>
                    <div class="line-spacer"></div>
                    <p><span> Datei zum Feedback: SelectedFile.pdf </span></p>
                    <p class="text-primary"><span> Kategorie: Untersuchungskonzept </span></p>
                    <hr />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                        <label class="form-check-label" for="defaultCheck1">
                            Das fand ich gut
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
                        <label class="form-check-label" for="defaultCheck1">
                            Ich habe noch eine Frage
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck3">
                        <label class="form-check-label" for="defaultCheck1">
                            Das wuerde ich anders machen
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck4">
                        <label class="form-check-label" for="defaultCheck1">
                            Ich habe eine Idee
                        </label>
                    </div>
                    <hr />

                    <div>
                        <table>
                            <tr>
                                <td  id="yourContent">
                                    <h2> Schreibe dein Feedback! </h2>

                                    <form id="descriptionform" class="form-journal" method="POST" action="../rest/projectdescription/saveText">

                                        <input type="hidden" name="student" value="0">
                                        <input type="hidden" name="project" value="0">

                                        <div class="description-form-container">

                                            <div class ="description-form-editor">
                                    <textarea id = "editor" name="text" form="descriptionform" >
                                    </textarea>
                                            </div>

                                            <div class="description-form-buttons">

                                                <button type="button" onclick="goBack()">Zur&uuml;ck</button>
                                                <button type="button" class="viewprojectstudent">Speichern</button>

                                            </div>
                                            <div>

                                            </div>

                                        </div>
                                    </form>


                                </td>
                            </tr>
                        </table>
                    </div>



                        <script>
                            function goBack() {
                                window.history.back();
                            }
                        </script>
                </tr>
                </td>


</tr>
</table>
</div>
</div>
</div>

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
</body>

</html>