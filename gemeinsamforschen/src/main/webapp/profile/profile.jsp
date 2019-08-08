


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Profil</title>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed|Open+Sans+Condensed:300' rel='stylesheet'
          type='text/css'>
    <script src="js/profile.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="col span_content">

        <div class="page-content-wrapper">
            <div class="container">
                <div class="row">
                    <%-- about --%>
                    <div class="col-sm-4">
                        <h3>&Uuml;ber mich</h3>
                        <ul class="list-group">
                            <li class="list-group-item">

                            </li>
                            <li class="list-group-item">
                                <p>Sonstiges:</p>
                                <form id="uploadimage" method="post" action="rest/user/student/wiepke">
                                    <div id="image_preview"><img id="previewing" src="../libs/img/noImg.png"/></div>
                                    <hr id="line">
                                    <div id="selectImage">
                                        <label>Select Your Image</label><br/>
                                        <input type="file" name="image" id="file" required/>
                                        <input type="submit" value="Upload" class="submit"/>
                                    </div>
                                </form>
                                <p id="message"></p>
                            </li>
                        </ul>

                    </div>

                    <%-- activites --%>
                    <div class="col-sm-4">
                        <h3>Aktivit&auml;t</h3>
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

                        <ul class="list-group">
                            <li class="list-group-item">
                                Quiz "Thermodynamik" ohne Fehler absolviert
                            </li>
                            <li class="list-group-item">
                                Dossier vollständig verfasst
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div class="col span_chat"><chat:chatWindow orientation="right" scope="project"/> <chat:chatWindow
            orientation="right" scope="group"/></div>
</main>
    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</div>
</body>

</html>