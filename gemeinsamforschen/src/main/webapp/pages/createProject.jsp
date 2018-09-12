<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/createProject.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div style="margin-left: 2%;">
            <br><br>
            <label>Projektname: <input placeholder="Projektname"></label>
            <label>Passwort: <input placeholder="Passwort"></label>
        </div>
        <button class="btn btn-default" type="button" id="submit">beitreten</button>
    </div>
</div>

<footer:footer/>
</body>

</html>