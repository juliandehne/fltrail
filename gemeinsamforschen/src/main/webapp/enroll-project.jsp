<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gruppenmatcher</title>
    <script src="core/config.js"></script>
    <script src="core/utility.js"></script>
    <script src="management/js/join-project.js"></script>

</head>

<body>
<div class="loader-inactive" id="loader"></div>
<div id="wrapper" class="wrapper">
    <div class="page-content-wrapper">
        <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
            <div class="row">
                <div class="col-md-12">
                    <h3>Tragen sie sich in ein neues Projekt ein. </h3>
                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>
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
</body>

</html>