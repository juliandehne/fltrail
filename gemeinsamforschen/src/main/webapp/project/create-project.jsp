<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 12.09.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Projekterstellung</title>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="../groupfinding/js/config.js"></script>
    <script src="../taglibs/js/unstructuredRest.js"></script>
    <script src="js/create-project.js"></script>
</head>

<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<div class="row group">

</div>
<main class="create-project">
    <div class="row">

        <div class="col span_content span_2_of_2">


                                <!-- <h3> Erstellen Sie ein neues Projekt.</h3> -->

            <div class="contact-clean">
                <label>Name des Projekts</label>
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
                    Tragen Sie einen Projektnamen ein.
                </div>
                <div class="form-group infotext">
                    <input class="form-control" name="name" placeholder="Name" id="nameProject">
                </div>
                <label> Passwort zum Teilnehmen (optional) </label>
                <div class="form-group infotext">
                    <input class="form-control" name="password" placeholder="Passwort" id="passwordProject">
                </div>
                <h3>Gruppenarbeitseinstellungen</h3>
                <ul>
                    <li id="lgLI">
                        <input type="radio" id="lg" name="gfm" value="Basierend auf Lernzielen">
                        <label for="lg">Basierend auf Interessen der Studenten</label>
                        <div class='explanation icon'><p>Studenten nennen Schlagworte, die ihre Interessen
                            bezüglich des Projekts betreffen.
                            Dabei werden Gruppen gebildet, deren Gruppenmitglieder ähnliche Interessen
                            vorweisen.</p></div>
                    </li>
                    <li>
                        <input type="radio" id="ml" name="gfm" value="per Hand">
                        <label for="ml">per Hand</label>
                        <div class='explanation icon'><p>Nachdem genug Studenten dem Projekt beigetreten sind
                            können Sie die Gruppen eigenständig zuordnen.</p></div>
                    </li>
                    <li id="bpLI">
                        <input type="radio" id="bp" name="gfm" value="Basierend auf Präferenzen">
                        <label for="bp">Basierend auf Persönlichkeitsmerkmalen</label>
                        <div class='explanation icon'><p>Studenten beantworten Fragen zu Ihrer Persönlichkeit,
                            wenn sie sich in das Projekt einschreiben wollen.
                            Dabei werden Gruppen gebildet, deren Gruppenmitglieder synergieren
                            während das Konfliktpotential minimiert wird.</p>
                            <label>Präferierte Gruppengröße <input value="3" type='number' min='3' id="userCount" style="width:40px;"></label>
                            <a data-toggle="collapse" href="#howToBuildGroups" role="button" aria-expanded="false" aria-controls="howToBuildGroups"><i class="fas fa-question"></i></a>
                            Mit dieser Gruppengröße benötigt das Projekt wenigstens <span id="groupSize">6</span> Teilnehmer
                            um Gruppen bilden zu können.
                            <div class="collapse" id="howToBuildGroups">
                                <div class="card card-body">
                                    Es werden so viele Gruppen mit Ihrer präferierten Gruppengröße gebildet wie möglich.
                                    Die verbleibenden Studenten werden dann zufällig auf die bestehenden Gruppen verteilt.
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <input type="radio" id="single" name="gfm" value="Keine Gruppen">
                        <label for="single">Einzelarbeit</label>
                        <div class='explanation icon'><p>Studenten arbeiten einzeln an ihren Projekten.</p></div>
                    </li>
                </ul>


                <h3>Projektbeschreibung</h3>

                <textarea class="form-control infotext" rows="5" id="projectDescription"
                          placeholder="meine Projektbeschreibung"></textarea>

                <h3>Tags </h3>
                <div id="tagHelper" class="alert alert-warning" style="width:475px;">
                    Fügen Sie zudem 5 Tags zu ihrem Projekt hinzu, welche Ihr Projekt inhaltlich umreißen.
                </div>
                <div class="form-group">
                    <input class="tags" data-role="tags" name="Tags" placeholder="Tags" id="tagsProject">
                </div>
                <%-- <label>An Kurs selbst teilnehmen <input type="checkbox" id="Teilnehmer"></label>--%>

                <div>
                    <h3>Bestandteile der studentischen Arbeit</h3>
                    <p>Bestimmen Sie welche Abschnitte Sie in der studentischen Ausarbeitung sehen wollen:</p>
                    <ul id="categoryList">

                    </ul>
                    <script id="categoryTemplate" type="text/x-jQuery-tmpl">
                        <li>
                            <label>
                                <input type="checkbox" class="category" value="${category}">
                                <span>${category}</span>
                            </label>
                        </li>

                    </script>
                </div>
                <div class="form-group">
                    <button class="primary" id="sendProject">Erstellen</button>
                </div>

            </div>
        </div>
        <div class="col span_chat">

        </div>
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>

</html>
