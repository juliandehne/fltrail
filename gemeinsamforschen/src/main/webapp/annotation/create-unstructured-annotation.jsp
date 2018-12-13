<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>

    <!-- css - unstructured-annotation -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-annotation.css">
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>

    <!-- js - jQuery validation plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"
            type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.js"
            type="text/javascript"></script>
    <!-- js - rangy Core -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-core.js" type="text/javascript"></script>
    <!-- js - rangy TextRange Module -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-textrange.js" type="text/javascript"></script>
    <!-- js - unstructuredRest -->
    <script src="js/unstructuredRest.js"></script>
    <!-- js - unstructuredUpload -->
    <script src="js/unstructuredAnnotation.js"></script>


</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">     <jsp:param name="hierarchy" value="1"/> </jsp:include> <main> <jsp:include page="../taglibs/timeLine.jsp" />
<div class="col span_content">
    <div class="content-mainpage">
        <div class="leftcolumn">
            <div class="leftcontent">
                <div class="leftcontent-text context-menu-one" id="documentText"></div>
                <div class="leftcontent-buttons">
                    <div class="leftcontent-buttons-save">
                        <button id="btnSave" type="button" class="btn btn-secondary">Speichern</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="rightcolumn">
            <div id="missingAnnotation" class="alert alert-warning"></div>
            <div class="rightcontent">
                <ol id="annotations">

                </ol>
                <script id="annotationTemplate" type="text/x-jQuery-tmpl">
                    <li class="spacing">
                    <div id="${annotationType}" class="category-card not-added">
                        <p>${annotationType}</p>
                    </div>
                </li>
                </script>
            </div>
        </div>
    </div>
</div>
<div class="col span_chat">
    <chat:chatWindow orientation="right" scope="project" />
    <chat:chatWindow orientation="right" scope="group" />
</div>
</main><jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
