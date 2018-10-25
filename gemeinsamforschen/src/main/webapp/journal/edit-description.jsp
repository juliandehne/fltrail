<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script  src="js/edit-description.js"></script>

</head>

<body>
<menu:menu hierarchy="1"/>
<h1> Projektbeschreibung bearbeiten </h1>

                        <form id="descriptionform" class="form-journal" method="POST"
                              action="rest/projectdescription/saveText">

                                    <textarea id="editor" name="text" form="descriptionform"></textarea>
                        </form>
<footer:footer/>
</body>

</html>