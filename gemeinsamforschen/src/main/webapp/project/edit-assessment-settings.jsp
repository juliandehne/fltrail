<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
</head>

<body>
<menu:menu hierarchy="1"/><div class="col span_content">
<h1> Bewertungsverfahren einstellen </h1>

<a href="edit-group-settings.jsp">Weiter </a>

</div><div class="col span_chat">     <chat:chatWindow orientation="right" scope="project" />     <chat:chatWindow orientation="right" scope="group" /> </div><footer:footer/>
</body>

</html>