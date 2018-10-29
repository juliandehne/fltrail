<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 12.09.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="../groupfinding/js/config.js"></script>
    <script src="js/deleteProject.js"></script>

</head>

<body>
<div class="loader-inactive" id="loader"></div>
<menu:menu hierarchy="1"/>
<headLine:headLine/>
<legend style="margin-left:13px;">Projektnamen</legend>
<input class="form-control" type="text" id="projectName" name="Project" required
       placeholder="Projekt1" autofocus
       style="max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
<div class="alert alert-warning" role="alert" id="projectIsMissing">
    Dieser Projektname existiert nicht
</div>
<button id="deleteProject" class="btn btn-danger">löschen</button>
<footer:footer/>
</body>

</html>