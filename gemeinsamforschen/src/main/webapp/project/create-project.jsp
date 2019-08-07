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

            <div class="col span_content span_l_of_2">


                <!-- <h3> Erstellen Sie ein neues Projekt.</h3> -->

                <div class="contact-clean">
                    <label>Name des Projekts</label>
                    <div class="alert alert-danger" role="alert" style="width:475px" id="projectNameExists">
                        Dieser Projektname exisitiert bereits.
                    </div>
                    <div class="alert alert-danger" role="alert" style="width:475px" id="specialChars">
                        Der Projektname darf keine Sonderzeichen enthalten.
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
                    <h3>Gruppenbildungsverfahren</h3>
                    <div class="alert alert-info">
                        Hier können Sie ein Verfahren wählen, das Studierende automatisiert in Forschungsgruppen
                        einteilt.
                        Dies geschieht nach unterschiedlichen Kriterien und optimiert je nach gewählten Verfahren die
                        voraussichtliche Kompatibilität der Gruppenmitglieder für eine erfolgreiche Bearbeitung des
                        Projekts.
                    </div>
                    <div class="alert alert-danger" role="alert" style="width:475px" id="groupMechanismMissing">
                        Bitte wählen Sie eine Methode die Gruppen in Ihrem Projekt zu bilden.
                    </div>
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
                            <label for="ml">Nicht automatisiert</label>
                            <div class='explanation icon'><p>Nachdem genug Studierende dem Projekt beigetreten sind,
                                können Sie die Gruppen eigenständig zuordnen.</p></div>
                        </li>
                        <li id="bpLI">
                            <input type="radio" id="bp" name="gfm" value="Basierend auf Präferenzen">
                            <label for="bp">Basierend auf Persönlichkeitsmerkmalen</label>
                            <div class='explanation icon'><p>Studenten beantworten Fragen zu ihrer Persönlichkeit,
                                wenn sie sich in das Projekt einschreiben wollen.
                                Dabei werden Gruppen gebildet, deren Gruppenmitglieder synergieren
                                während das Konfliktpotential minimiert wird.</p>
                                <label>Präferierte Gruppengröße <input value="3" type='number' min='3' id="userCount"
                                                                       style="width:40px;"></label>
                                <a data-toggle="collapse" href="#howToBuildGroups" role="button" aria-expanded="false"
                                   aria-controls="howToBuildGroups"><i class="fas fa-question"></i></a>
                                Mit dieser Gruppengröße benötigt das Projekt wenigstens <span id="groupSize">6</span>
                                Teilnehmer
                                um Gruppen bilden zu können.
                                <div class="collapse" id="howToBuildGroups">
                                    <div class="card card-body">
                                        Es werden so viele Gruppen mit Ihrer präferierten Gruppengröße gebildet wie
                                        möglich.
                                        Die verbleibenden Studenten werden dann zufällig auf die bestehenden Gruppen
                                        verteilt.
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
                    <div class="alert alert-info">
                        Mit dieser Beschreibung geben Sie einen Überblick über Ihr Projekt.
                    </div>
                    <div class="alert alert-danger" role="alert" style="width:475px" id="projectDescriptionMissing">
                        Geben Sie eine Beschreibung für Ihr Projekt an.
                    </div>
                    <textarea class="form-control infotext" rows="5" id="projectDescription"
                              placeholder="Meine Projektbeschreibung"></textarea>

                    <h3 style="margin-top: 10px;">Themen </h3>
                    <div id="tagHelper" class="alert alert-warning">
                        <div class="alert alert-info">
                            Geben Sie 5 Stichwörter an, welche Ihr Projekt inhaltlich umreißen.<br>
                            Dies hilft Studierenden dabei ihre Interessen mit den Bereichen des Kurses abzugleichen und
                            bietet den Gruppenbildungsverfahren Anhaltspunkte.
                        </div>
                    </div>
                    <div class="alert alert-danger" role="alert" style="width:475px" id="exactNumberOfTags">
                        Es müssen genau 5 Tags eingegeben werden.
                    </div>
                    <div class="form-group">
                        <input class="tags" data-role="tags" name="Tags" placeholder="Stichwort" id="tagsProject">
                    </div>
                    <%-- <label>An Kurs selbst teilnehmen <input type="checkbox" id="Teilnehmer"></label>--%>

                    <div>
                        <h3>Bestandteile der studentischen Arbeit</h3>
                        <div class="alert alert-info">
                            Innerhalb Ihres Projektes werden Studierende ein Forschungsdossier anfertigen. Dieses
                            Dossier
                            kann verschiedene Abschnitte beinhalten, mit denen die Studierenden dann arbeiten werden.
                        </div>
                        <p>Bestimmen Sie welche Abschnitte Sie in der studentischen Ausarbeitung sehen wollen:</p>
                        <ul id="categoryList">

                        </ul>
                        <ul style="margin-bottom: 5px;" id="ownCategoryList">
                            <li class="LIOwnCategory">
                                <input type="checkbox"
                                       onclick="$('#ownCategoryTemplate').tmpl({}).appendTo('#ownCategoryList');"
                                       title="Bestandteil">
                                <input type="text" class="category" title="Bestandteil">
                            </li>
                        </ul>
                        <div class="alert alert-warning" id="noSpecialCharacters" hidden>
                            <p>Verwenden Sie hier keine Umlaute oder Sonderzeichen.</p>
                        </div>
                        <script id="categoryTemplate" type="text/x-jQuery-tmpl">
                        <li>
                            <label>
                                <input type="checkbox" class="category" value="${category}">
                                <span>${categoryName}</span>
                            </label>
                        </li>




                        </script>
                        <script id="ownCategoryTemplate" type="text/x-jQuery-tmpl">
                        <li class="LIOwnCategory">
                            <input type="checkbox" onclick="$('#ownCategoryTemplate').tmpl({}).appendTo('#ownCategoryList');">
                            <input type="text" class="category">
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
</div>
    <jsp:include page="../taglibs/footer.jsp"/>

</body>

</html>
