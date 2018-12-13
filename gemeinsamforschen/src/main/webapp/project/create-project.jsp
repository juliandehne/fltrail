<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 12.09.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>


<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projekterstellung</title>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="../groupfinding/js/config.js"></script>
    <script src="js/create-project.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="col span_content">
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
            <div class="alert alert-danger" role="alert" style="width:475px" id="projectDescriptionMissing">
                Geben Sie eine Beschreibung für Ihr Projekt an.
            </div>
            <div class="alert alert-danger" role="alert" style="width:475px" id="projectIsMissing">
                Tragen sie einen Projektnamen ein.
            </div>
            <div class="form-group">
                <input class="form-control" name="name" placeholder="Name" id="nameProject">
            </div>
            <p> Passwort zum Teilnehmen (optional) </p>
            <div class="form-group">
                <input class="form-control" name="password" placeholder="Passwort" id="passwordProject">
            </div>
            <p>Gruppenarbeitseinstellungen</p>
            <ul>
                <li>
                    <input type="radio" id="lg" name="gfm" value="Basierend auf Lernzielen">
                    <label for="lg">Basierend auf Lernzielen</label>
                </li>
                <li>
                    <input type="radio" id="ml" name="gfm" value="per Hand">
                    <label for="ml">per Hand</label>
                </li>
                <li>
                    <input type="radio" id="bp" name="gfm" value="Basierend auf Präferenzen">
                    <label for="bp">Basierend auf Präferenzen</label>
                </li>
                <li>
                    <input type="radio" id="single" name="gfm" value="Keine Gruppen">
                    <label for="single">Einzelarbeit</label>
                </li>
            </ul>
            <h4>Projektbeschreibung</h4>
            <div>
            <textarea class="" rows="4" cols="60" id="projectDescription"
                      placeholder="meine Projektbeschreibung"></textarea>
            </div>
            <h4>Tags </h4>
            <div id="tagHelper" class="alert alert-warning" style="width:475px;">
                Fügen sie zudem 5 Tags zu ihrem Projekt hinzu, welche ihr Projekt inhaltlich umreißen.
            </div>
            <div class="form-group">
                <input class="tags" data-role="tags" name="Tags" placeholder="Tags" id="tagsProject">
            </div>
            <%-- <label>An Kurs selbst teilnehmen <input type="checkbox" id="Teilnehmer"></label>--%>

            <div class="form-group">
                <button class="btn btn-primary" style="margin-left:129px;" id="sendProject">erstellen</button>
            </div>
        </div>
    </div>
    <div class="col span_chat">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>

</body>

</html>
