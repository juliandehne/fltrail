<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/config.js"></script>
    <script src="js/create-preferences.js"></script>
</head>
<body>

<div class="loader-inactive" id="loader">
    <div class="sk-cube1 sk-cube"></div>
    <div class="sk-cube2 sk-cube"></div>
    <div class="sk-cube4 sk-cube"></div>
    <div class="sk-cube3 sk-cube"></div>
</div>
<menu:menu hierarchy="1"/>
<div class="col span_content">
    <h3>Geben Sie hier ihre Präferenzen ein!</h3>
    <fieldset>
        <legend>Passwort        </legend>
        <input class="form-control" type="text" placeholder="******" id="projectPassword">
        <div class="alert alert-danger" id="projectWrongPassword">Das Passwort ist falsch.</div>
    </fieldset>
    <fieldset id="competenciesFieldSet">
        <legend>Lernziele</legend>
        <div id="competencies">
            <input class="form-control" type="text" id="competencies0" name="competencies" required=""
                   placeholder="Ich möchte folgendes lernen:">
        </div>
        <button
                class="btn btn-default" type="button"
                style="height:36px;width:33px;"
                id="addCompetenceButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                style="height:36px;width:33px;"
                id="subtractCompetenceButton">-
        </button>

    </fieldset>
    <fieldset id="researchQuestionFieldSet">
        <legend>Forschungsfrage</legend>
        <div id="researchQuestion">
            <input class="form-control" id="researchQuestion0" type="text" name="researchQuestion" required=""
                   placeholder="Meine Forschungsfrage(n): ">
        </div>
        <button class="btn btn-default" type="button"
                style="height:36px;width:33px;"
                id="addResearchQuestionButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                style="height:36px;width:33px;"
                id="subtractCResearchQuestionButton">-
        </button>
    </fieldset>
    <fieldset>
        <legend>Tags</legend>
        <p class="alert alert-warning" style="width:520px;">Wähle 2 der hier angegebenen Tags aus, die am ehesten zu
            deiner Forschungsfrage passen.</p>
        <div id="tags">

        </div>
    </fieldset>
    <button class="btn btn-primary" id="studentFormSubmit">
        eintragen
    </button>
    <div class="alert alert-warning" style="width:520px; margin-top: 10px;" role="alert">
        Das Verarbeiten der Lernziele und das Gruppenmatching kann einen Moment dauern!
    </div>
</div>
<div class="col span_chat">
    <chat:chatWindow orientation="right" scope="project"/>
    <chat:chatWindow orientation="right" scope="group"/>
</div>
<div class="cover"></div>
<footer:footer/>
</body>
</html>