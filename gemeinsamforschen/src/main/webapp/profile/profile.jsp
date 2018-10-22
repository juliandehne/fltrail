<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed|Open+Sans+Condensed:300' rel='stylesheet'
          type='text/css'>
    <script src="js/profile.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>

<div class="page-content-wrapper">
    <headLine:headLine/>
    <div class="container">
        <div class="row">
            <%-- about --%>
            <div class="col-sm-4">
                <h3>&Uuml;ber mich</h3>
                <%-- TODO: retrieve profile data --%>
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
<footer:footer/>
</body>

</html>