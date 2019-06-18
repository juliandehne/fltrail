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

    <script src="js/create-groups-manual.js"></script>
    <%--<link rel="stylesheet" href="css/create-groups-manual.css">--%>
</head>
<script id="groupTemplate" type="text/x-jQuery-tmpl">

<div class="grouplists" id="${groupName}">
    <ul class="complex-list" ondragover="allowDrop(event);" ondrop="dropContent(event);">
        <li class="label">
            <button type="button" class="group-button list-group-item list-group-item-action">${groupName}</button>
        </li>
    {{each groupMember}}
        <li draggable="true" ondragstart="allowDrag(event);" ondrop="noDropHere(event);" id="${name}_${groupName}">
            <button type="button" name="student"
            class="student-button list-group-item list-group-item-action">
                <span>${name}</span>
                <p name="userEmail" >${email}</p>
            </button>
        </li>
    {{/each}}
        <li>
            <p name="chatRoomId" hidden>${chatRoomId}</p>
        </li>
    </ul>
</div>


</script>

<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<!-- back-->
<div class="row group nav">
    <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> zurück zu den Aufgaben</i></a>
</div>
<main class="groups-manual">

    <div class="row group">

        <h2>Gruppeneinteilung</h2>

        <div class="info-message">
            <p>Wählen Sie die Studenten an, die sie verschieben wollen. Dann die Gruppe,
                in die die Studenten verschoben werden sollen und klicken sie auf "Personen verchieben".</p>
        </div>

        <button id="btnRelocate" class="spacer-horizontal primary">Personen verschieben</button>
        <button id="openNewGroup" class="spacer-horizontal primary">Neue Gruppe öffnen</button>
        <button id="deselectStudents" class="spacer-horizontal primary">Selektion aufheben</button>


        <div class="col span_content span_2_of_2">
            <div style="...">
                <div style="display: flex">
                    <div style="display:block">
                        <div id="studentsWithoutGroup" class="alert alert-warning">
                            Es sind noch Studenten "gruppenlos".
                        </div>
                        <div id="taskCompleted" class="alert alert-success">
                            Gruppen wurden gespeichert.
                        </div>

                        <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject">
                            <div style="..." id="gruppenlos" class="grouplists">
                                <ul class="complex-list">
                                    <li class="label">
                                        <button type="button"
                                                class="group-button list-group-item list-group-item-action active">
                                            Nicht zu geordnet
                                        </button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="button" class="primary" style="height:50px;" id="btnSave">Gruppen speichern</button>
        </div>

        <div class="col span_chat"></div>


    </div>

</main>
<jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>
</html>
