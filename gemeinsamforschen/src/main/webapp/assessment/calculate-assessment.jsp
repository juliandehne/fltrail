<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!-- TODO refactor @Sven: bitte nutze einen Unterordner für dein Modul z.B. webapp/annotation/ -->


<!DOCTYPE html>
<html lang="en">
<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/assessmentCalculator.js"></script>
</head>
<body>
<menu:menu/>
<button id="calculateNow">Post Performance</button>
<button id="giveItBack">Get TotalPerformance</button>
<headLine:headLine/>
</body>
</html>