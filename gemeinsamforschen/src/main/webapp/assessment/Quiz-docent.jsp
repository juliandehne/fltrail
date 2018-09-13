<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/Quiz-docent.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <table class="table-striped">
            <tbody id="tableQuiz">

            </tbody>
        </table>
        <button class="btn btn-primary" id="newQuiz">neues Quiz</button>

    </div>
    <footer:footer/>
</div>
</body>
</html>
