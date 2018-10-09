<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/edit-group-settings.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>
<div id="wrapper">
<h1> Gruppenverfahren festlegen </h1>


<p>Legen Sie das Verfahren fest, nach dem Gruppen gebildet werden sollen:</p>
<input type="radio" id="lg" name="gfm" value="Basierend auf Lernzielen">
<label for="lg">Basierend auf Lernzielen</label>
<input type="radio" id="ml" name="gfm" value="per Hand">
<label for="ml">per Hand</label>
<input type="radio" id="bp" name="gfm" value="Basierend auf Präferenzen">
<label for="bp">Basierend auf Präferenzen</label>
<button class="btn btn-primary" style="margin-left:129px;" id="selectGFM">wählen</button>

</div>
</body>

</html>