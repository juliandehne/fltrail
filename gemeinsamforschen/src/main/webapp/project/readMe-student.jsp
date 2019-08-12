<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 04.10.2018
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Hilfe</title>

    <script src="js/tasks.js"></script>
    <script src="js/solve-inCardTasks.js"></script>
</head>
<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main class="project-overview">

        <div class="row group">
            <div class="col span_timeline timeline span_s_of_2">
                <ul>
                    <li class="icon phase1"><a href="#Gruppenbildung">Gruppenbildung</a></li>
                    <li class="icon phase2 "><a href="#Entwurf">Entwurf</a></li>
                    <li class="icon phase4"><a href="#Durchfuhrung">Durchführung</a></li>
                    <li class="icon phase5"><a href="#Bewertung">Bewertung</a></li>
                    <li class="icon phase6"><a href="#Projektabschluss">Projektabschluss</a></li>
                </ul>

            </div>
            <div class="col span_l_of_2">
                <h3 class="phase-heading card-draft">FL-Trail</h3>
                <div class="card card-draft current readMe">
                    <div class="col icon infotask"
                         style="width:10%;padding:.5em;background:#FFF;box-sizing: border-box;">

                    </div>
                    <div class="col span_l_of_2">
                        Dieses Tool dient der Unterstützung von studentischen Forschungsprojekten. Es wird den Ablauf
                        in ihrem Kurs vorstrukturieren.

                    </div>
                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-grouping" id="Gruppenbildung">Gruppenbildung</h3>
                <div class="card card-grouping current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        In der Gruppenbildungsphase werden die Studierende in Projektgruppen eingeteilt.
                        Einzelarbeit wird zwar softwareseitig unterstützt, ist aber nicht empfohlen.
                        Wie in richtigen Forschungsprojekten gehen wir davon aus, dass kooperatives Arbeiten gewünscht ist.
                        Das Werkzeug wird daraufhin optimierte Arbeitsgruppen bilden.
                    </div>

                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-feedback" id="Entwurf">Entwurf</h3>
                <div class="card card-feedback current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <%--<img src="../taglibs/img/dossier-example.jpg">--%>
                        Nach der Gruppenfindungsphase werden die Studierenden beauftragt ein Forschungsentwurf/Dossier zu erarbeiten.
                            In diesem plant ihr euer Projekt und die Methodik zur Beantwortung der gewählten
                            Forschungsfrage.
                            Die Studierende geben sich daraufhin Feedback zu den erarbeiteten Forschungskonzepten
                            bezüglich üblicher Kategorien wie der Recherche, der gewählten Methodik etc.
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-execution" id="Durchfuhrung">Durchführung</h3>
                <div class="card card-execution current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <%--<img src="../taglibs/img/dossiers.jpg">--%>
                        Wir haben die Erfahrung gemacht, dass es für die Studierenden motivierend ist, wenn sie das erarbeitete Konzept auch in einem Projekt durchführen. Da die Durchführung für die Fächer sehr unterschiedlich abläuft, haben wir hier keine spezifische Unterstützung implementiert. Es ist jedoch empirisch belegt, dass komplexe Prozesse wie die eines selbstgesteuerten Lernprojektes gut unterstützt werden können, indem die Reflexion mittels gezielter Fragen und Portfoliotechniken angeregt wird.
                    </div>

                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-assessment" id="Bewertung">Bewertung</h3>
                <div class="card card-assessment current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <%--<img src="../taglibs/img/assessment.png">--%>
                        In der Peer-Assessment-Phase werden Sie aufgefordert, ihre Kommilitonen zu bewerten.  Der Dozent wird ebenfalls eine Bewertung vornehmen. Für die finale Note wird dem Dozenten daraufhin eine kombinierte Note vorgeschlagen, die dieser übernehmen kann. Ob dieses Verfahren gewählt wird, wird in ihrem Kurs geklärt.
                    </div>

                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading GRADING" id="Projektabschluss">Projektabschluss</h3>
                <div class="card GRADING current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                    <%--    <img src="../taglibs/img/Notenscan.jpg">
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.v<br>--%>
                        Das Projekt ist zu Ende und Sie haben eine Note erhalten.
                    </div>


                    <div style="clear:left"></div>
                </div>

            </div>

        </div> <!-- end row -->
    </main>
</div>
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>
</html>