<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/view-quiz.js"></script>

</head>

<body>
<menu:menu hierarchy="1"/><div class="col span_content">
<div id="wrapper">
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>Quiz for project1 </h1>
                        <!-- here will be all the content -->
                        <table class="table-striped">
                            <tbody id="tableQuiz">

                            </tbody>
                        </table>
                        <button id="deleteQuiz" class="btn btn-danger">Quiz löschen</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </div><div class="col span_chat">     <chat:chatWindow orientation="right" scope="project" />     <chat:chatWindow orientation="right" scope="group" /> </div><footer:footer/>
</div>
</body>

</html>