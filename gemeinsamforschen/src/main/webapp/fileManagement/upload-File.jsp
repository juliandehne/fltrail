<%--
  Created by IntelliJ IDEA.
  User: fides-WHK
  Date: 05.04.2019
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/upload-File.js"></script>
</head>
<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="project-overview">


    <!-- this is what we are here for -->
    <div class="col span_content span_l_of_3">
        Please upload your presentation: <input type="file" id="fileInput" accept=".pptx, .pdf"/> <br>
        Thank you <br>
        <button id="upload">submit</button>
    </div>
    <!-- this is what we are here for -->



    <div class="col span_chat span_l_of_3 right">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
        <a id="groupView" style="cursor:pointer;">Gruppenansicht</a>
    </div>
    <div class="row">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>

</body>
</html>