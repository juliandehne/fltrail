<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>

    <script src="js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/Sidebar-Menu.js"></script>
    <script  src="js/create-journal.js"></script>
        --%>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../journal/css/create-journal.css">
    <script src="js/peerfeedback.js"></script>
    <omniDependencies:omniDependencies hierarchy="1"/>
</head>

<body>
<menu:menu hierarchy="1"/>
<div id="wrapper">
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <h2> Gib dein Feedback ein!</h2>
                    <div class="line-spacer"></div>
                    <p><span> Datei zum Feedback: SelectedFile.pdf </span></p>
                    <p class="text-primary"><span> Kategorie: Untersuchungskonzept </span></p>
                    <hr/>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                        <label class="form-check-label" for="defaultCheck1">
                            Das fand ich gut
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
                        <label class="form-check-label" for="defaultCheck1">
                            Ich habe noch eine Frage
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck3">
                        <label class="form-check-label" for="defaultCheck1">
                            Das wuerde ich anders machen
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck4">
                        <label class="form-check-label" for="defaultCheck1">
                            Ich habe eine Idee
                        </label>
                    </div>
                    <hr/>

                    <div>
                        <table>
                            <tr>
                                <td id="yourContent">
                                    <h2> Schreibe dein Feedback! </h2>

                                    <form id="form" method="POST" action="../rest/peerfeedback/save">
                                        <%--id="journalform" class="form-journal"--%>
                                        <input type="hidden" id="student" name="student">
                                        <input type="hidden" id="project" name="project">
                                        <input type="hidden" id="feedbackid" name="id">
                                        <input type="hidden" id="reciever" name="reciever">
                                        <input type="hidden" id="sender" name="sender">
                                        <input type="hidden" id="filename" name="filename">
                                        <input type="hidden" id="category" name="category">
                                        <input type="hidden" id="filename" name="filename">


                                        <div class="journal-form-container">

                                            <div class="journal-form-editor">
                                                <textarea id="editor" name="text"></textarea> <%--form="journalform"--%>
                                            </div>

                                            <div class="journal-form-buttons">
                                                <input class="btn btn-default btn-sm" type="submit">
                                                <a id="backLink" class="btn btn-default btn-sm"> Zur&uuml;ck </a>
                                            </div>

                                            <div>
                                                <p id="as">Now what</p>
                                                <input type="button" value="get txt" onclick="go()"/>
                                            </div>

                                        </div>
                                    </form>


                                </td>
                            </tr>
                        </table>
                    </div>


                    <script>
                        function goBack() {
                            window.history.back();
                        }
                    </script>
                </tr>
                </td>


                </tr>
            </table>
        </div>
    </div>
    <footer:footer/>
</div>
</body>

</html>