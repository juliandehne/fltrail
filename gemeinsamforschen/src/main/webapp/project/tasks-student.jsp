<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 04.10.2018
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/all.css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="css/normalize.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito:300,400,700" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <title>Projecttitle | Gemeinsam Forschen</title>
</head>
<body>
<%--<header>
    <div class="row ">
        <div class="nav-group-left">
            <a>Home</a>
            <a>Meine Projekte</a>
        </div>
        <div class="nav-group-right">
            <a>Wiki</a>
            <a>Einstellungen</a>
            <a>Logout</a>

        </div>
    </div>


</header>--%>

<main>
    <div class="row group">
        <div class="titlerow">

        </div>
    </div>

    <div class="row group nav">
        <a href="" >[<i class="fas fa-chevron-left"> zurueck ]</i></a>
    </div>

    <div class="row group">

        <div class="col span_s_of_2 .timeline">
            <ul>
                <li class="neutral icon closed">Projektinitialisierung</li>
                <li class="draft icon closed">Entwurfsphase</li>
                <li class="feedback icon ">Feedbackphase</li>
                <li class="icon inactive">Reflextionsphase</li>
                <li class="icon inactive" >Assessment</li>
                <li class="icon inactive">Noten</li>

            </ul>
        </div>

        <div class="col span_l_of_2"> <!-- col right-->
            <h1>Projektname</h1>

            <div class="infotext ">
                <p class="icon">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
                </p>
            </div>

            <!-- Aufgabe -->
            <div class="card ">
                <div class="col span_s_of_2 icon infotask">

                </div>

                <div class="col span_l_of_2">
                    <h4>Du wurdest einer Forschungsgruppe hinzugefügt</h4>
                </div>


                <div style="clear:left"></div>

            </div>

            <!-- Aufgabe -->
            <div class="card card-draft">
                <div class="col span_s_of_2 icon grouptask">

                </div>

                <div class="col span_l_of_2">
                    <button class="primary">Lege ein Dossier an </button>
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
                        <li><a href="#"></a>Feedback 2</a></li>

                    </ul>
                </div>
                <div class="status icon"><p>Noch drei Tage Zeit</p></div>
                <div style="clear:left"></div>

            </div>

            <!-- Aufgabe -->
            <div class="card card-reflection">
                <div class="col span_s_of_2 icon usertask">
                </div>

                <div class="col span_l_of_2">
                    <button class="primary">Schreibe ein Feedback </button>
                    <a href="#">Regeln fürs Feedback schreiben</a>
                </div>
                <div class="status alert icon"><p>Du bist zu spät.</p></div>
                <div style="clear:left"></div>

            </div>


            <!-- Aufgabe -->
            <div class="card card-presentation ">
                <div class="col span_s_of_2 icon grouptask ">

                </div>

                <div class="col span_l_of_2">
                    <button class="primary">Schließe das Dossier ab </button>
                    <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</p>
                </div>
                <div class="status icon"></div>
                <div style="clear:left"></div>

            </div>

            <!-- Aufgabe -->
            <div class="card card-assessment ">
                <div class="col span_s_of_2 icon grouptask ">

                </div>

                <div class="col span_l_of_2">
                    <button class="primary">Schließe das Dossier ab </button>
                    <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</p>
                </div>
                <div class="status icon"><p>Noch drei Tage Zeit</p></div>
                <div style="clear:left"></div>

            </div>

            <!-- Aufgabe -->
            <div class="card card-grades ">
                <div class="col span_s_of_2 icon grouptask ">

                </div>

                <div class="col span_l_of_2">
                    <button class="primary">Schließe das Dossier ab </button>
                    <br>
                    <input type="checkbox" name="vehicle1" value="Bike">  Is erledigt <br>
                    <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</p>
                </div>
                <div class="status icon"></div>
                <div style="clear:left"></div>

            </div>
        </div>
    </div>

    <div style="clear:left"></div>
    <!-- schmale spalte links, weite spalte rechts-->
    <!--        <div class="row  group">

        <div class="col span_l_of_2">
        This is column 1
        </div>

        <div class="col span_s_of_2">
        This is column 2
        </div>

    </div> -->

</main>
<footer>
    <p>
        Impressum </br>
        Ansprechpartner</br>
        Fides</br>
    </p>
</footer>

</body>
</html>