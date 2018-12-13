<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>
    <script src="js/Quiz-docent.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">     <jsp:param name="hierarchy" value="1"/> </jsp:include> <main> <jsp:include page="../taglibs/timeLine.jsp" /><div class="col span_content">
<div id="wrapper">
    <div class="page-content-wrapper">
        <table class="table-striped">
            <tbody id="tableQuiz">

            </tbody>
        </table>
        <button class="btn btn-primary" id="newQuiz">neues Quiz</button>

    </div>
    </div><div class="col span_chat">     <chat:chatWindow orientation="right" scope="project" />
    <chat:chatWindow orientation="right" scope="group" />
</div>
    <jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>
</html>
