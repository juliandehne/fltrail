<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 12.09.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<omniDependencies:omniDependencies hierarchy="1"></omniDependencies:omniDependencies>
<script type="text/javascript" src="../libs/jQuery-Tags-Input-master/src/jquery.tagsinput.js"></script>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projekterstellung</title>
    <script src="../core/config.js"></script>
    <script src="js/create-project.js"></script>
</head>

<body>

<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div>
                        <p></p>
                        <h3> Erstellen Sie ein neues Projekt.</h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="contact-clean">
        <p>Name des Projekts</p>
        <div class="alert alert-danger" role="alert" style="width:475px" id="projectNameExists">
            Dieser Projektname exisitiert bereits.
        </div>
        <div class="alert alert-danger" role="alert" style="width:475px" id="exactNumberOfTags">
            Es müssen genau 5 Tags eingegeben werden.
        </div>
        <div class="alert alert-danger" role="alert" style="width:475px" id="specialChars">
            Der Projektname darf keine Sonderzeichen enthalten.
        </div>
        <div class="alert alert-danger" role="alert" style="width:475px" id="projectIsMissing">
            Tragen sie einen Projektnamen ein.
        </div>
        <div class="form-group"><input class="form-control" name="name" placeholder="Name"
                                       style="width:286px;margin-left:50px;" id="nameProject"></div>
        <p> Passwort zum Teilnehmen (optional) </p>
        <div class="form-group"><input class="form-control" name="password" placeholder="Passwort"
                                       style="width:287px;margin-left:51px;" id="passwordProject"></div>
        <p> Passwort zum Löschen (sonst: 1234) </p>
        <div class="form-group"><input class="form-control" name="adminpassword" placeholder="Passwort"
                                       style="width:287px;margin-left:51px;" id="adminPassword"></div>
        <p>Tags </p>
        <div id="tagHelper" class="alert alert-warning" style="width:475px;">
            Fügen sie zudem 5 Tags zu ihrem Projekt hinzu, welche ihr Projekt inhaltlich umreißen.
        </div>
        <div class="form-group"><input class="tags" data-role="tags" name="Tags" placeholder="Tags"
                                       id="tagsProject">
        </div>
       <%-- <label>An Kurs selbst teilnehmen <input type="checkbox" id="Teilnehmer"></label>--%>

        <div class="form-group">
            <button class="btn btn-primary" style="margin-left:129px;" id="sendProject">erstellen</button>
        </div>
    </div>
</div>

</body>

</html>
