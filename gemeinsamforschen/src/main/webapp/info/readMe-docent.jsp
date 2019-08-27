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

    <script src="../project/js/tasks.js"></script>
    <script src="../project/js/solve-inCardTasks.js"></script>
    <link href="css/info.css" rel="stylesheet">
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
                    <li class="icon phase4"><a href="#Durchfuhrung">Projekt</a></li>
                    <li class="icon phase5"><a href="#Bewertung">Bewertung</a></li>
                    <li class="icon phase6"><a href="#Projektabschluss">Projektabschluss</a></li>
                </ul>

            </div>
            <div class="col span_l_of_2">
                <h3 class="phase-heading card-draft">FL-Trail</h3>
                <div class="card card-draft current readMe">
                    <div class="col icon infotask readMeDIV">
                    </div>
                    <div class="col span_l_of_2">
                        <img src="pics/path.JPEG">
                        Dieses Werkzeug kann Sie darin unterstützen forschendes Lernen in ihrem Kurs durchzuführen. Forschendes
                        Lernen verfolgt das Ziel, dass Studierende eine längerfristige Perspektive auf ihr Fach und ihre
                        Identität als Forschende entwickeln anstatt von Klausur zu Klausur zu denken. Daher wird mit den
                        Studierenden der vollständige Forschungszyklus durchgespielt, der jedoch an einigen Stellen auf
                        den pädagogischen Kontext angepasst wird. Da dies für Einsteigende sehr aufwändig erscheint,
                        haben wir aus empirischen Studien haben einen Vorschlag für einen Ablauf erstellt. Dieser wird
                        durch dieses Tool so passgenau unterstützt, dass sich der Aufwand für Lehrende auf ein Minimum
                        reduziert:
                    </div>
                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-grouping" id="Gruppenbildung">Gruppenbildung</h3>
                <div class="card card-grouping current readMe">
                    <div class="col span_s_of_2 icon infotask readMeDIV"></div>

                    <div class="col span_l_of_2">
                        <img src="pics/groups.JPEG">
                        In der Gruppenbildungsphase werden Studierende in Projektgruppen eingeteilt. Einzelarbeit wird
                        zwar softwareseitig unterstützt, ist aber nicht empfohlen. Wie in richtigen Forschungsprojekten
                        gehen wir davon aus, dass kooperatives Arbeiten gewünscht ist. Dabei können Sie als dozierende
                        Person wählen, ob sie die Gruppen homogen basierend auf ähnlichen thematischen Interessen oder
                        heterogen mit einer optimalen Verteilung von Persönlichkeitsmerkmalen bilden lassen wollen. Das
                        Werkzeug wird ihnen daraufhin optimierte Gruppen bilden, die Sie übernehmen können oder noch
                        anpassen.
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-feedback" id="Entwurf">Entwurf</h3>
                <div class="card card-feedback current readMe">
                    <div class="col span_s_of_2 icon infotask readMeDIV"></div>

                    <div class="col span_l_of_2">
                        <img src="pics/concept.JPEG">
                        <%--<img src="../taglibs/img/dossier-example.jpg">--%>
                        Nach der Gruppenfindungsphase werden Studierende beauftragt ein Forschungsentwurf/Dossier zu
                        erarbeiten. Je nach geplantem Kurs kann dies auch die finale Abgabe des Kurses sein, wodurch die
                        späteren Module weggelassen werden können. Studierende geben sich daraufhin Feedback zu den
                        erarbeiteten Forschungskonzepten bezüglich üblicher Kategorien wie der Recherche, der gewählten
                        Methodik etc. Diese Kategorien müssen sie schon zu Beginn bei der Projekterstellung festlegen.
                        Dieses Feedback wird daraufhin eingearbeitet.
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-execution" id="Durchfuhrung">Projekt</h3>
                <div class="card card-execution current readMe">
                    <div class="col span_s_of_2 icon infotask readMeDIV"></div>

                    <div class="col span_l_of_2">
                        <img src="pics/project.JPEG">
                        <%--<img src="../taglibs/img/dossiers.jpg">--%>
                        Wir haben die Erfahrung gemacht, dass es für Studierende motivierend ist, wenn sie das
                        erarbeitete Konzept auch in einem Projekt durchführen. Manchmal wird dies durch einen
                        zweisemestrigen Kurs realisiert, oder durch eine Projektphase im Sommer. Wenn dies geplant ist,
                        sollten sie sich nicht zu lange mit der Dossiererstellung aufhalten. Da die Durchführung für die
                        Fächer sehr unterschiedlich abläuft, haben wir hier keine spezifische Unterstützung
                        implementiert. Es ist jedoch empirisch belegt, dass komplexe Prozesse wie die eines
                        selbstgesteuerten Lernprojektes gut unterstützt werden können, indem die Reflexion mittels
                        gezielter Fragen und Portfoliotechniken angeregt wird.
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-assessment" id="Bewertung">Bewertung</h3>
                <div class="card card-assessment current readMe">
                    <div class="col span_s_of_2 icon infotask readMeDIV"></div>

                    <div class="col span_l_of_2">
                        <img src="pics/assessment.JPEG">
                        Häufig haben Dozierende wenig Einblick in die Gruppenarbeit von Studierenden,
                        so dass solche Techniken als hilfreich wahrgenommen werden.
                        Wir haben Formeln entwickelt, die die Peer-Noten in zweierlei Hinsicht korrigieren.
                        Zum einen wird ein zu großes Delta zu der Note der dozierenden Person aufgezeigt,
                        zum anderen werden Cheating oder anderer Bias entdeckt und bestraft.
                        Es wird analog zu der Gruppenphase nur ein Vorschlag für eine Notengebung für Sie als dozierende
                        Person entwickelt,
                        den Sie daraufhin übernehmen können oder auch nicht.
                    </div>
                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading GRADING" id="Projektabschluss">Projektabschluss</h3>
                <div class="card GRADING current readMe">
                    <div class="col span_s_of_2 icon infotask readMeDIV"></div>

                    <div class="col span_l_of_2">
                        <%--<img src="../taglibs/img/Notenscan.jpg">--%>
                        Das Projekt ist zu Ende und Sie können die finalen Noten der Studierenden als Excel-Sheet
                        exportieren.
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