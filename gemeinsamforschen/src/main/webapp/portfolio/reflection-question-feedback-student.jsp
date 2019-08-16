<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Reflexionsfragen-Feedback</title>

    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>

    <link rel="stylesheet" type="text/css" href="css/show-portfolio.css">

    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>

    <script src="js/reflection-question-feedback-student.js"></script>
    <script src="js/reflection-question-feedback-shared.js"></script>
    <script src="../taglibs/js/apiClient/reflectionQuestionClient.js"></script>
    <script src="../taglibs/js/apiClient/contributionFeedbackClient.js"></script>
    <script src="js/portfolio-shared.js"></script>
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
                <div id="portfolioTemplateResult"></div>
                <script id="portfolioTemplate" type="text/x-jsrender">
                    <h3>Feedback zu Ihren Antworten auf die Reflexionsfragen</h3>
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
