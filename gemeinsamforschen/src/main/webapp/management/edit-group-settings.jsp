<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/edit-group-settings.js"></script>
</head>

<body>

<h1> Gruppenverfahren festlegen </h1>


<p>Legen Sie das Verfahren fest, nach dem Gruppen gebildet werden sollen:</p>
<input type="radio" id="lg" name="gfm" value="Basierend auf Lernzielen">
<label for="lg">Basierend auf Lernzielen</label>
<input type="radio" id="ml" name="gfm" value="per Hand">
<label for="ml">per Hand</label>
<input type="radio" id="bp" name="gfm" value="Basierend auf Präferenzen">
<label for="bp">Basierend auf Präferenzen</label>
<button class="btn btn-primary" style="margin-left:129px;" id="selectGFM">wählen</button>

<%--
<a href="../groupfinding/create-create-groups-learninggoal.jsp?token=<%=request.getParameter("token")+"&projectToken="+request.getParameter("projectToken") %>" > Weiter </a>
--%>


</body>

</html>