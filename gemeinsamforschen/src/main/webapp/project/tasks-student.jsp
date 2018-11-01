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
<!DOCTYPE html>
<html lang="de">
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/tasks.js"></script>
</head>
<body>
<menu:menu hierarchy="1"/>
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
        </div>


    </script>
<footer:footer/>

</body>
</html>