<%@ page import="unipotsdam.gf.core.management.ManagementImpl" %>
<%@ page import="unipotsdam.gf.core.management.user.User" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
</head>


<%
    // Retrieve user to be used here
    String token = request.getParameter("token");
    ManagementImpl management = new ManagementImpl();
    User user =  management.getUserByToken(token);
%>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId">
                <a href="#">
                        <span class="glyphicon glyphicon-envelope"
                              style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
                </a>
                <a href="#">
                    <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
                </a></h1>
        </div>
        <div id="content">

            <div class="page-header text-center">
                <h1>Mein Profil</h1>
            </div>

            <div class="container">
                <div class="row">
                    <%-- about --%>
                    <div class="col-sm-4">
                        <h3>&Uuml;ber mich</h3>
                        <%-- TODO: retrieve profile data --%>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <p>Name: <%=user.getName()%></p>
                            </li>
                            <li class="list-group-item">
                                <p>Sonstiges:</p>
                            </li>
                        </ul>

                    </div>

                    <%-- activites --%>
                    <div class="col-sm-4">
                        <h3>Aktivit&auml;t</h3>
                        <%-- TODO: Retrieve achievements from database--%>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <p>
                                    Forschungsfrage erstellt
                                    <a href="#">
                                        <span class="glyphicon glyphicon-link"></span>
                                    </a>
                                </p>
                            </li>
                            <li class="list-group-item">
                                <p>
                                    Quiz "Goethe" erstellt
                                    <a href="#">
                                        <span class="glyphicon glyphicon-link"></span>
                                    </a>
                                </p>
                            </li>
                            <li class="list-group-item">
                                <p>
                                    Quiz "Schiller-Test" bearbeitet (3/5)
                                    <a href="#">
                                        <span class="glyphicon glyphicon-link"></span>
                                    </a>
                                </p>
                            </li>
                            <li class="list-group-item">
                                <p>
                                    Günther reviewed
                                    <a href="#">
                                        <span class="glyphicon glyphicon-link"></span>
                                    </a>
                                </p>
                            </li>
                        </ul>
                    </div>

                    <%-- achievements --%>
                    <div class="col-sm-4">
                        <h3>Erfolge</h3>
                        <%-- TODO: get achievements --%>

                        <ul class="list-group">
                            <li class="list-group-item">
                                Quiz "Thermodynamik" ohne Fehler absolviert
                            </li>
                            <li class="list-group-item">
                                Dossier vollständig hochgeladen
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>

</body>

</html>