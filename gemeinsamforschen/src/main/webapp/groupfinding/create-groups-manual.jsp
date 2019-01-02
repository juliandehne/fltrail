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
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/create-groups-manual.js"></script>
    <link rel="stylesheet" href="css/create-groups-manual.css">
</head>

<script id="groupTemplate" type="text/x-jQuery-tmpl">
        <div style="display: block; margin-top: 5px; margin-left: 5px;" id="${groupName}">
        <button type="button" class="group-button list-group-item list-group-item-action">${groupName}</button>
        {{each groupMember}}
            <button type="button" name="student" class="student-button list-group-item list-group-item-action">
            <span>${name}</span>
            <p name="userEmail" hidden>${email}</p></button>
        {{/each}}
        <p name="chatRoomId" hidden>${chatRoomId}</p>
        </div>

</script>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="groups-manual">
    <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content span_l_of_2">
        <div style="display: block">
            <div style="display: flex">
                <div style="display:block">
                    <div id="studentsWithoutGroup" class="alert alert-warning">
                        Es sind noch Studenten "gruppenlos".
                    </div>
                    <div id="done" class="alert alert-success">
                        Gruppen wurden gespeichert.
                    </div>
                    Gruppen:
                    <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject">
                        <div style="display:block;" id="gruppenlos">
                            <button type="button" class="group-button list-group-item list-group-item-action active">
                                gruppenlos
                            </button>
                        </div>
                    </div>
                    <button id="openNewGroup">neue Gruppe öffnen</button>
                </div>

            </div>
            <div style="margin-top: 50px; margin-left: 5px;margin-right: 5px;">
                <button id="btnRelocate">&lt&ltverschieben&gt&gt</button>
            </div>
        </div>
        <button type="button" class="btn-success" id="btnSave"> speichern</button>
    </div>
    <div class="col span_chat">
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>
</html>
