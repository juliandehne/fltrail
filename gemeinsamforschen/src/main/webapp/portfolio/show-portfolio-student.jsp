<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Portfolio</title>

    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>

    <link rel="stylesheet" type="text/css" href="../taglibs/css/dropDownButton.css">
    <link rel="stylesheet" type="text/css" href="css/show-portfolio.css">

    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <!-- js - unstructuredRest -->
    <script src="../taglibs/js/unstructuredRest.js"></script>
    <script src="js/portfolio-student.js"></script>
    <script src="js/portfolio-shared.js"></script>
    <script src="../taglibs/js/apiClient/contributionFeedbackClient.js"></script>
    <script src="../taglibs/js/dropDownButton.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>


</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> </i> Zurück zu den
            Aufgaben</a>
    </div>
    <main id="show_portfolio">
        <div class="row group">

            <div class="col span_2_of_2">

                <h3>E-Portfolio</h3>
                <div id="visibilityTemplateResult"></div>
                <script id="visibilityTemplate" type="text/x-jsrender">
                <div></div>
                    <button class="btn btn-primary" onclick="clickedCreatePrivatePortfolio()">Eintrag erstellen</button>
                      <hr class="spacer-nofloat" style="border-color:transparent">
                     <label>Einträge</label><br>
                    <div class="dropdown fltrailselect">
                        <button class="dropbtn" onclick='dropDownClick("myDropdown")'>{{:currentVisibility.buttonText}}
                        </button>
                        <div class="dropdown-content" id="myDropdown">
                            {{for possibleVisibilities}}
                                <a id={{:name}} onclick='visibilityButtonPressed("{{:#index}}")'>{{:buttonText}}</a>
                            {{/for}}
                        </div>
                    </div>
            </script>
            <div id="portfolioTemplateResult"></div>
            <script id="portfolioTemplate" type="text/x-jsrender">
                {{include tmpl="#portfolioEntryTemplate"/}}
            </script>
                <jsp:include page="submission-entries-template.jsp"/>

            </div> <!-- end 2 of 2 -->
        </div><!-- end row -->
    </main>
</div> <!-- flex wrapper -->
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>
