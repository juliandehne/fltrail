<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!-- TODO refactor @Sven: bitte nutze einen Unterordner für dein Modul z.B. webapp/annotation/ -->


<!DOCTYPE html>
<html lang="en">
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/assessmentCalculator.js"></script>
</head>
<body>
<menu:menu hierarchy="1"/><div class="col span_content">
<button id="calculateNow">Post Performance</button>
<button id="giveItBack">Get TotalPerformance</button>
<headLine:headLine/>
</body>
</html>