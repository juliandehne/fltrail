<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 04.10.2018
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/tasks.js"></script>
</head>
<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <jsp:include page="../taglibs/timeLine.jsp"/>

    <div class="col span_content">

        <%--    <div class="infotext ">
            <p class="icon">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt
                ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et
                ea rebum.
            </p>
        </div>--%>
        <div id="listOfTasks">

        </div>
        <script id="taskTemplate" type="text/x-jQuery-tmpl">
   <div class="card ${phase}">
       <div class="col span_s_of_2 icon ${taskType}">
       </div>
       <div class="col span_l_of_2" id="${taskName}">
           {{if infoText}}
               <h4>${infoText}</h4>
           {{/if}}
           {{if solveTaskWith}}
               <button class='primary' onClick='${solveTaskWithLink}'>${solveTaskWith}</button>
           {{/if}}
           {{if helpLink}}
               <div style="width:100%"><a href='${helpLink}'>Hier</a> bekommst du Hilfe.</div>
           {{/if}}
       </div>
       {{if timeFrame}}
           {{html timeFrame}}
       {{/if}}
       <div style="clear:left"></div>
   </div>

        </script>

        <script id="finishedTaskTemplate" type="text/x-jQuery-tmpl">
<div class="card ${phase}">
   <div class="card-finished"><h4 class="icon closed">${infoText}</h4>
   {{html timeFrame}}
   </div>
</div>

        </script>
    </div>
    <div class="col span_chat">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
    </div>
</main>
</main>
<jsp:include page="../taglibs/footer.jsp"/>

</body>
</html>