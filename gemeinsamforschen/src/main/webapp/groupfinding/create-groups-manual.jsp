<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 18.09.2018
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/create-groups-manual.js"></script>
    <link rel="stylesheet" href="css/create-groups-manual.css">
</head>
<body>
<menu:menu hierarchy="1"/>
<div style="display: block">
    <div style="display: flex">
        <div style="display:block">
            Gruppen:
            <div class="list-group" style="display: flex" id="groupsInProject">
                <div style="display:block;" id="gruppenlos">
                    <button type="button" class="group-button list-group-item list-group-item-action active">
                        gruppenlos
                    </button>
                </div>
                <script id="groupTemplate" type="text/x-jQuery-tmpl">
        <div style="display: block" id="${groupName}">
        <button type="button" class="group-button list-group-item list-group-item-action">${groupName}</button>
        {{each groupMember}}
            <button type="button" name="student" class="student-button list-group-item list-group-item-action">${name}</button>
        {{/each}}
        </div>


                </script>
            </div>
        </div>

    </div>
    <div style="margin-top: 50px; margin-left: 5px;margin-right: 5px;">
        <button id="btnRelocate">&lt&ltverschieben&gt&gt</button>
    </div>
</div>
<button type="button" class="btn-success" id="btnSave"> speichern</button>
<footer:footer/>
</body>
</html>
