<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/overview-student.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>
<div id="wrapper">
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table id="projects">  <!-- getElementById('projects').append um neue Projekte anzufügen -->
                <script id="projectTRTemplate" type="text/x-jQuery-tmpl">
                    <tr class="pageChanger">
                    <td>
                        <a id="project_${projectName}">
                            <h1>${projectName}</h1>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Newsfeed </h3>
                                Status: <p id="status_${projectName}"></p>
                            </div>
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <span>dummy</span>
                                    </li>
                                    <li class="list-group-item">
                                        <span>dummy</span>
                                    </li>
                                    <li class="list-group-item">
                                        <span>dummy</span></li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                </tr>

                </script>
            </table>
        </div>
        <button class="btn btn-default" type="button" style="margin-left:250px;" id="enrollProject">Projekt beitreten
        </button>
    <%--    <iframe height="600px" id="rocketChat" src="http://fleckenroller.cs.uni-potsdam.de:3000">
        </iframe>--%>
    </div>
</div>


</body>

</html>