<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
</head>

<body>

<h1> Bewertungsverfahren einstellen </h1>

<a
        href="edit-group-settings.jsp?token=<%=request.getParameter("token")+"&projectToken="+request.getParameter("projectToken") %>">
    Weiter </a>


</body>

</html>