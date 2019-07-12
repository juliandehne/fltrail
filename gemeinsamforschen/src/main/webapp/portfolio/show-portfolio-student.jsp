<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>

    <link rel="stylesheet" type="text/css" href="../taglibs/css/dropDownButton.css">
    <link rel="stylesheet" type="text/css" href="css/show-portfolio.css">

    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>

    <!-- js - unstructuredRest -->
    <script src="../taglibs/js/unstructuredRest.js"></script>
    <script src="js/portfolio-student.js"></script>
    <script src="../taglibs/js/dropDownButton.js"></script>
    <script src="../taglibs/js/apiClient/contributionFeedbackClient.js"></script>
    <script src="js/portfolio-shared.js"></script>

</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>
    <main>
        <div class="row group">
            <h1>E-Portfolio</h1>
            <div id="visibilityTemplateResult"></div>
            <script id="visibilityTemplate" type="text/x-jsrender">
                <div></div>
                    <button class="btn btn-primary" onclick="clickedCreatePrivatePortfolio()">Persönlichen Eintrag erstellen</button>
                    <h4>Wähle, welche Beiträge du sehen möchtest</h4>
                    <div class="dropdown">
                        <button class="dropbtn btn btn-primary" onclick='dropDownClick("myDropdown")'>{{:currentVisibility.buttonText}}
                            <i class="fa fa-caret-down"></i>
                        </button>

                        <div class="dropdown-content" id="myDropdown">
                            {{for possibleVisibilities}}
                                <a id={{:name}} onclick='visibilityButtonPressed("{{:name}}")'>{{:buttonText}}</a>
                            {{/for}}
                        </div>
                    </div>



            </script>
            <h3>Einträge</h3>
            <div id="portfolioTemplateResult"></div>

            <script id="portfolioTemplate" type="text/x-jsrender">
                {{include tmpl="#portfolioEntryTemplate"/}}
            </script>
            <jsp:include page="portfolio-commentary-template.jsp"/>
        </div> <!-- flex wrapper -->
    </main>
    <jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
