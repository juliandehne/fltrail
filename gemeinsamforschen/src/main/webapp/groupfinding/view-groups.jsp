<%@ page contentType="text/html;charset=UTF-8" %>
<%--
&lt;%&ndash;
  Created by IntelliJ IDEA.
  User: dehne
  Date: 18.09.2018
  Time: 13:36
  To change this template use File | Settings | File Templates.
&ndash;%&gt;
--%>

<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/view-groups.js"></script>
    <link rel="stylesheet" href="css/create-groups-manual.css">
</head>
<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>


    <main id="seeFeedback" class="">
        <div class="row group">
            <div class="col span_2_of_2">
                <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject"></div>
                <script id="groupTemplate" type="text/x-jQuery-tmpl">
                     <div style="" class="grouplists" id="${groupName}">
                         <ul class="complex-list">
                             <li class="label">
                                 <div type="button" class="list-group-item list-group-item-action">${groupName}</div>
                             </li>
                             {{each groupMember}}
                             <li>
                                 <div type="button" name="student" class="list-group-item list-group-item-action">
                                     <span>name: ${name}</span>
                                     <p name="userEmail">E-mail: ${email}</p>
                                     {{if discordid}}
                                         <p name="discordId"> discordId: ${discordid}</p>
                                     {{/if}}
                                 </div>
                             </li>
                             {{/each}}
                             <li>
                                 <p name="chatRoomId" hidden>${chatRoomId}</p>
                             </li>
                         </ul>
                     </div>


                </script>
            </div>
        </div>
    </main>
</div>
    <jsp:include page="../taglibs/footer.jsp"/>

</body>
</html>
