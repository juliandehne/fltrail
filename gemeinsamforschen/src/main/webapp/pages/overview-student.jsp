<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>fltrail</title>
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/overview-student.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper">

        <h1>Übersicht für Student1</h1>
        <a href="#"><span class="glyphicon glyphicon-envelope"
                          style="font-size:27px;margin-top:-17px;margin-left:600px;"></span></a>
        <a href="#"><span class="glyphicon glyphicon-cog"
                          style="font-size:29px;margin-left:5px;margin-top:-25px;"></span></a>
        <div>
            <table id="projects">  <!-- getElementById('projects').append um neue Projekte anzufügen -->
                <tr style="cursor:pointer" role="button">
                    <td>
                        <a id="project1Link">
                            <h1>dummy Projekt1</h1>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style="width:100px;"></div>
                        <div style="width:741px;">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Newsfeed </h3>
                                </div>
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <span>erste Abgabe vom Dozenten zu dd.mm.yyyy gefordert</span>
                                        </li>
                                        <li class="list-group-item"><span>Beitrag von Student1 wurde hochgeladen</span>
                                        </li>
                                        <li class="list-group-item">
                                            <span>Gruppe "gemeinsam forschen" rockt das Haus</span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr style="cursor:pointer" role="button">
                    <td>
                        <a href="project-docent.jsp">
                            <h1>dummy Projekt2</h1>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style="width:100px;"></div>
                        <div style="width:741px;">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Newsfeed </h3>
                                </div>
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <span>erste Abgabe vom Dozenten zu dd.mm.yyyy gefordert</span>
                                        </li>
                                        <li class="list-group-item"><span>Beitrag von Student1 wurde hochgeladen</span>
                                        </li>
                                        <li class="list-group-item">
                                            <span>Gruppe "gemeinsam forschen" rockt das Haus</span></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <button class="btn btn-default" type="button" style="margin-left:250px;">Projekt beitreten</button>
    </div>
</div>


</body>

</html>