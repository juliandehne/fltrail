<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>student-form-design</title>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="css/Login-Form-Clean.css">
    <link rel="stylesheet" href="css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="css/Sidebar-Menu.css">
    <link rel="stylesheet" href="css/Sidebar-Menu1.css">

    <script src="js/config.js"></script>
    <script src="js/create-groups-learninggoal.js"></script>
    <script src="js/Sidebar-Menu.js"></script>


</head>

<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div class="page-content-wrapper">
            <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
                <div class="row">
                    <div class="col-md-12">

                        <div class="page-header"></div>
                    </div>
                </div>
            </div>
        </div>

        <div>
            <div class="container">
                <div class="row">
                    <div class="col-md-offset-3 col-sm-8 col-xs-1">
                        <h3>Projekt</h3>
                        <div class="dropdown">
                            <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"
                                    id="projectDropdown">Projekt auswählen
                                <span class="caret"></span></button>
                            <ul class="dropdown-menu" id="dropdownOptions">
                            </ul>
                        </div>
                    </div>
                </div>
                <div id="tablesHolder"></div>
            </div>
        </div>
    </div>
    <div class="col span_chat">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>

</html>