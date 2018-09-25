<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"></omniDependencies:omniDependencies>
    <script src="../core/config.js"></script>
    <script src="js/create-preferences.js"></script>
</head>
<body>

<div class="loader-inactive" id="loader">
    <div class="sk-cube1 sk-cube"></div>
    <div class="sk-cube2 sk-cube"></div>
    <div class="sk-cube4 sk-cube"></div>
    <div class="sk-cube3 sk-cube"></div>
</div>
<div id="wrapper" class="wrapper">
    <div class="page-content-wrapper">
        <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
            <div class="row">
                <div class="col-md-12">
                    <h3>Geben Sie hier ihre Präferenzen ein!</h3>
                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>

    <fieldset>
        <legend style="margin-left:13px;">Lernziele</legend>
        <div id="competencies">
            <input class="form-control" type="text" id="competencies0" name="competencies" required=""
                   placeholder="Ich möchte folgendes lernen:"
                   style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        </div>
        <button
                class="btn btn-default" type="button"
                style="margin-left:443px;margin-top:-88px;height:36px;width:33px;"
                id="addCompetenceButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                style="margin-left:10px;margin-top:-88px;height:36px;width:33px;"
                id="subtractCompetenceButton">-
        </button>

    </fieldset>
    <fieldset style="margin-bottom:-3px;">
        <legend style="margin-left:13px;">Forschungsfrage</legend>
        <div id="researchQuestion">
            <input class="form-control" id="researchQuestion0" type="text" name="researchQuestion" required=""
                   placeholder="Meine Forschungsfrage(n): "
                   style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        </div>
        <button class="btn btn-default" type="button"
                style="margin-left:443px;margin-top:-88px;height:36px;width:33px;"
                id="addResearchQuestionButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                style="margin-left:10px;margin-top:-88px;height:36px;width:33px;"
                id="subtractCResearchQuestionButton">-
        </button>
    </fieldset>
    <fieldset>
        <legend style="margin-left:13px;">Tags</legend>
        <p class="alert alert-warning" style="width:520px;">Wähle 2 der hier angegebenen Tags aus, die am ehesten zu
            deiner Forschungsfrage passen.</p>
        <div id="tags">

        </div>
    </fieldset>
    <button class="btn btn-primary" id="studentFormSubmit" style="width:90px;margin-left:169px;margin-top:13px;">
        eintragen
    </button>
    <div class="alert alert-warning" style="width:520px" role="alert">
        Das Verarbeiten der Lernziele und das Gruppenmatching kann einen Moment dauern!
    </div>
</div>

</body>




