<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/Quiz.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu/>
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
