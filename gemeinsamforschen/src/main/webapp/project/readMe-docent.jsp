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
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text
                        dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text
                        dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text
                        dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text
                        dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text
                        dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text
                        dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>

                    </div>
                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-grouping" id="Gruppenbildung">Gruppenbildung</h3>
                <div class="card card-grouping current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.<br>
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-feedback" id="Entwurf">Entwurf</h3>
                <div class="card card-feedback current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <img src="../taglibs/img/dossier-example.jpg">
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-execution" id="Durchfuhrung">Durchführung</h3>
                <div class="card card-execution current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <img src="../taglibs/img/dossiers.jpg">
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading card-assessment" id="Bewertung">Bewertung</h3>
                <div class="card card-assessment current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <img src="../taglibs/img/assessment.png">
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                    </div>


                    <div style="clear:left"></div>
                </div>

                <h3 class="phase-heading GRADING" id="Projektabschluss">Projektabschluss</h3>
                <div class="card GRADING current readMe">
                    <div class="col span_s_of_2 icon infotask"></div>

                    <div class="col span_l_of_2">
                        <img src="../taglibs/img/Notenscan.jpg">
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.v<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.Text dummy.<br>
                        Text dummy.Text dummy.Text dummy.Text dummy.v<br>
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