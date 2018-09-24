<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>
<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="../core/config.js"></script>
    <script src="js/join-project.js"></script>


</head>

<body>
<div id="wrapper" class="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <fieldset>
            <legend style="margin-left:13px;">Projektnamen</legend>
            <input class="form-control" type="text" id="projectName" name="Project" required=""
                   placeholder="Projekt1" autofocus=""
                   style="max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
            <div class="alert alert-warning" role="alert" id="projectIsMissing">
                Dieser Projektname existiert nicht.
            </div>

        </fieldset>
        <fieldset>
            <legend style="margin-left:13px;">Passwort</legend>
            <input class="form-control" type="password" id="projectPassword" name="Password" required=""
                   placeholder="******"
                   style="max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
            <div class="alert alert-warning" role="alert" id="projectWrongPassword">
                Falsches Passwort.
            </div>
        </fieldset>
        <button id="seeProject" class="btn btn-primary">Einsehen</button>
    </div>
</div>
</body>

</html>