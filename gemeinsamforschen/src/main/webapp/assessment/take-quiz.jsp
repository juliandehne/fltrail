<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/take-quiz.js"></script>

</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main> <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div id="wrapper">
            <div class="page-content-wrapper">
                <div>
                    <table>
                        <tr>
                            <td id="yourContent">
                                <h1>Quiz for gemeinsamForschen </h1>
                                <!-- here will be all the content -->


                                <table class="table-striped">
                                    <tbody id="tableQuiz">

                                    </tbody>
                                </table>
                                <button id="submitQuiz" class="btn btn-success">Antwort speichern</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="col span_chat">
            <chat:chatWindow orientation="right" scope="project"/>
            <chat:chatWindow orientation="right" scope="group"/>
        </div>
        <jsp:include page="../taglibs/footer.jsp"/>
    </div>
</body>

</html>