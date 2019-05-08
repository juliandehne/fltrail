<%@ page contentType="text/html;charset=UTF-8" %>


<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/Quiz.js"></script>
</head>

<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main> <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div id="wrapper">
            <div class="page-content-wrapper">
                <table class="table-striped">
                    <tbody id="myQuizzes">

                    </tbody>
                </table>
                <button class="btn btn-primary" id="newQuiz">neues Quiz</button>

            </div>
        </div>
        <div class="col span_chat"><chat:chatWindow orientation="right" scope="project"/>
            <chat:chatWindow orientation="right" scope="group"/>
        </div>
        <jsp:include page="../taglibs/footer.jsp"/>
    </div>
</main>
</div>
</body>
</html>
