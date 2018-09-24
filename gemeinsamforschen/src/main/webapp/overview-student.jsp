<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="0"/>
    <script src="core/overview-student.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="0"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table id="projects">  <!-- getElementById('projects').append um neue Projekte anzufügen -->

            </table>
        </div>
        <button class="btn btn-default" type="button" style="margin-left:250px;" id="enrollProject">Projekt beitreten</button>
    </div>
</div>


</body>

</html>