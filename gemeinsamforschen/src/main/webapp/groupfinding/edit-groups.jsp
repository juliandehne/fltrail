<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 05.11.2018
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <%--<link rel="stylesheet" href="/resources/demos/style.css">--%>
    <link rel="stylesheet" href="css/edit-groups.css">
    <script src="js/edit-groups.js"></script>

</head>
<body>

<%--TODO das mit %3 oder %4 auf mehrere Zeilen verteilen--%>
<script id="groupTemplate" type="text/x-jQuery-tmpl">
        {{each(prop,group) bla}}
            <div class="container-fluid">
                <h3>Gruppe ${group.id}</h3>
                <ul id="group_${group.id}" class="droptrue sortableGroup">
                {{each(prop2, val) group.members }}
                    <li class="ui-state-default">
                    <table>
                        <tr>
                            <td> ${val.name}  </td>
                            <td> ${val.email} </td>
                        </tr>
                    </table>
                    </li>
                {{/each}}
                </ul>
            </div>
        {{/each}}
        <div class="container-fluid">
        <h3>Gruppe ${lastGroupId}</h3>
        <ul id="group_${lastGroupId}" class="droptrue sortableGroup"></ul>
        </div>


</script>
<div id="editable_groups"></div>
<button id="persistNewGroups">Gruppen speichern</button>
<button id="finalizeNewGroups">Gruppen finalisieren</button>

</body>
</html>
