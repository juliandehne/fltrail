<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/Quiz.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>
<div id="wrapper">
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <table class="table-striped">
            <tbody id="myQuizzes">

            </tbody>
        </table>
        <button class="btn btn-primary" id="newQuiz">neues Quiz</button>

    </div>
    <footer:footer/>
</div>
</body>
</html>
