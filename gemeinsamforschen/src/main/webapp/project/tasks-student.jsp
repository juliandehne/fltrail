<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 04.10.2018
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>

    <title>Projecttitle | Gemeinsam Forschen</title>
</head>
<body>
<menu:menu hierarchy="1"/>
<div class="col span_l_of_2"> <!-- col right-->
    <headLine:headLine/>
    <div class="infotext ">
        <p class="icon">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt
            ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et
            ea rebum.
        </p>
    </div>
    <div id="listOfTasks">

    </div>
    <script id="taskTemplate" type="text/x-jQuery-tmpl">
        <div class="card ${phase}">
            <div class="col span_s_of_2 icon ${taskType}">
            </div>
                <div class="col span_l_of_2">
                    ${infoText}
                    ${solveTaskWith}
                    ${helpLink}
                </div>
                ${timeFrame}
                <div style="clear:left"></div>
            </div>
            <a id="project_${projectName}">
                <h1>${projectName}</h1>
            </a>
        </div>
    </script>

    <!-- Aufgabe -->
    <div class="card ">


    </div>

    <!-- Aufgabe -->
    <div class="card card-draft">
        <div class="col span_s_of_2 icon grouptask">

        </div>

        <div class="col span_l_of_2">
            <button class="primary">Lege ein Dossier an</button>
            <a href="#">Erfahre etwas über Forschungsdossiers</a>
        </div>

        <div class="status icon"><p>Noch drei Tage Zeit</p></div>
        <div style="clear:left"></div>

    </div>
    <!-- Aufgabe -->
    <div class="card card-feedback">
        <div class="col span_s_of_2 icon infotask">
        </div>

        <div class="col span_l_of_2">
            <h4> Erhalte 3 Feedbacks </h4>
            <div class="shoulds">
                <i class="fas fa-check-circle"></i>&nbsp;
                <i class="fas fa-check-circle"></i>&nbsp;
                <i class="far fa-circle"></i>
            </div>
            <ul class="list">
                <li><a href="#">Feedback 1</a></li>
                <li><a href="#">Feedback 2</a></li>

            </ul>
        </div>
        <div class="status icon"><p>Noch drei Tage Zeit</p></div>
        <div style="clear:left"></div>

    </div>

    <!-- Aufgabe -->
    <div class="card card-grouping">
        <div class="col span_s_of_2 icon usertask">
        </div>

        <div class="col span_l_of_2">
            <button class="primary">Schreibe ein Feedback</button>
            <a href="#">Regeln fürs Feedback schreiben</a>
        </div>
        <div class="status alert icon"><p>Du bist zu spät.</p></div>
        <div style="clear:left"></div>

    </div>


    <!-- Aufgabe -->
    <div class="card card-execution">
        <div class="col span_s_of_2 icon grouptask ">

        </div>

        <div class="col span_l_of_2">
            <button class="primary">Schließe das Dossier ab</button>
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
                et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea
                rebum.</p>
        </div>
        <div class="status icon"></div>
        <div style="clear:left"></div>

    </div>

    <!-- Aufgabe -->
    <div class="card card-assessment">
        <div class="col span_s_of_2 icon grouptask ">

        </div>

        <div class="col span_l_of_2">
            <button class="primary">Schließe das Dossier ab</button>
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
                et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea
                rebum.</p>
        </div>
        <div class="status icon"><p>Noch drei Tage Zeit</p></div>
        <div style="clear:left"></div>

    </div>

    <!-- Aufgabe -->
    <div class="card card-grades">
        <div class="col span_s_of_2 icon grouptask ">

        </div>

        <div class="col span_l_of_2">
            <button class="primary">Schließe das Dossier ab</button>
            <br>
            <input type="checkbox" name="vehicle1" value="Bike"> Is erledigt <br>
            <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
                et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea
                rebum.</p>
        </div>
        <div class="status icon"></div>
        <div style="clear:left"></div>

    </div>
</div>
<footer:footer/>

</body>
</html>