<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <omniDependencies:omniDependencies/>
    <script src="js/assessmentCalculator.js"></script>
</head>
<body>
<menu:menu hierarchy="1"/>
<button id="calculateNow">Post Performance</button>
<button id="giveItBack">Get TotalPerformance</button>
<headLine:headLine/>
</body>
</html>