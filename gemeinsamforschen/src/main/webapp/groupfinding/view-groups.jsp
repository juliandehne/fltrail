<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 18.09.2018
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/view-groups.js"></script>
    <link rel="stylesheet" href="css/create-groups-manual.css">
</head>
<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<!-- back-->
<div class="row group nav">
    <a href="" ><i class="fas fa-chevron-circle-left"> zurück zu den Aufgaben</i></a>
</div>
<jsp:include page="view-groups-body.jsp"/>
<jsp:include page="../taglibs/footer.jsp"/>
</body>
</html>
