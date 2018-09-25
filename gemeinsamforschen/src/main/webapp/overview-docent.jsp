<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="0"/>
    <script src="core/overview-docent.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="0"/>
    <div class="page-content-wrapper">

        <headLine:headLine/>
            <table id="projects">  <!-- getElementById('projects').append um neue Projekte anzufügen -->
                <tr class="pageChanger">
                    <td>
                        <a id="project1Link">
                            <h1>gemeinsamForschen</h1>
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
                <tr class="pageChanger">
                    <td>
                        <a id="project2Link">
                            <h1>Kaleo</h1>
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

        <button class="btn btn-default" type="button" id="createProject" style="margin-left:250px;">Projekt
            erstellen</button>
    </div>
</body>

</html>