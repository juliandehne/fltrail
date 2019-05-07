<%@ page contentType="text/html;charset=UTF-8" %>

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
<main class="project-overview">
    <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content span_l_of_2 tasklist">
        <div id="listOfTasks">

        </div>
        <script id="taskTemplate" type="text/x-jQuery-tmpl">

  <h3 class="phase-heading ${phase} ">${phase}</h3>
   <div class="card ${phase}">
       <div class="col span_s_of_2 icon ${taskType}">
       </div>
       <div class="col span_l_of_2" id="${taskName}">
           {{if infoText}}
               <p class="task-info">${infoText}</p>
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
   </div>


        </script>

        <script id="finishedTaskTemplate" type="text/x-jQuery-tmpl">
            <div class="card-finished">
                <h3 class="icon closed phase-heading ${phase} " {{if !timeFrame}}style="color:lightgray;"{{/if}}><span>${infoText}</span></h3>
           <p style="text-align:center;">{{html timeFrame}}</p>
            </div>
        </script>

    </div>
    <div class="col span_chat">
        <chat:chatWindow orientation="right" scope="project"/>
        <%--<chat:chatWindow orientation="right" scope="group"/>--%>
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>
</html>
