<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
</head>

<body>

<h1> Projektbeschreibung bearbeiten </h1>

<a
        href="edit-assessment-settings.jsp?token=<%=request.getParameter("token")+"&projectToken="+request.getParameter("projectToken") %>" > Weiter </a>


</body>

</html>