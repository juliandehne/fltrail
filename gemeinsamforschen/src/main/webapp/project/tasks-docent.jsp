<%--
  todo: probably this page is not needed at all. as well as tasks.js
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
<div class="col span_l_of_2"> <!-- col right-->
    <headLine:headLine/>

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
                    <button class='primary' onClick="${solveTaskWithLink}">${solveTaskWith}</button>
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
</div>
<footer:footer/>
</body>
</html>
