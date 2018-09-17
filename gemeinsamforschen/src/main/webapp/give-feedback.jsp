<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../journal/css/create-journal.css">
    <script src="feedback/js/givepeerfeedback.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <h2> Gib dein Feedback ein!</h2>
                    <div class="line-spacer"></div>
                    <hr />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                        <label class="form-check-label" for="defaultCheck1" id="Check1">
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
                    <hr />

                    <div>
                        <table>
                            <tr>
                                <td  id="yourContent">
                                    <h2> Schreibe dein Feedback! </h2>

                                    <form  id= "journalform" method="POST" action="../rest/peerfeedback/save">

                                        <input type="hidden" id="student" name="student">
                                        <input type="hidden" id="project" name="project">
                                        <input type="hidden" id="feedbackid" name="id">
                                        <input type="hidden" id="rec" name="rec">
                                        <input type="hidden" id="sender" name="sender">
                                        <input type="hidden" id="timestamp" name="timestamp">
                                        <input type="hidden" id="category" name="cat">
                                        <input type="hidden" id="filename" name="filename">


                                        <div class="journal-form-category">
                                            Kategorie:
                                            <select name="category" form="journalform">
                                                <option value="TITEL"> Titel</option>
                                                <option value="RECHERCHE"> Recherche</option>
                                                <option value="LITERATURVERZEICHNIS"> Literaturverzeichnis</option>
                                                <option value="FORSCHUNGSFRAGE"> Forschungsfrage</option>
                                                <option value="UNTERSUCHUNGSKONZEPT"> Untersuchungskonzept</option>
                                                <option value="METHODIK"> Methodik</option>
                                                <option value="DURCHFUEHRUNG"> Durchführung</option>
                                                <option value="AUSWERTUNG"> Auswertung</option>

                                            </select>
                                        </div>

                                        <div class="journal-form-category">
                                            Feedbackempfänger:
                                            <select name="reciever" id="reciever" form="journalform"> <%--form="journalform"--%>
                                                <option value="sandra"> sandra </option>
                                            </select>
                                        </div>

                                        <div class="journal-form-container">

                                            <div class="journal-form-editor">
                                                <textarea id="editor" name="text" form="journalform"></textarea> <%--form="journalform"--%>
                                            </div>

                                            <div class="journal-form-buttons">
                                                <input class="btn btn-default btn-sm" type="submit">
                                                <a id="backLink" class="btn btn-default btn-sm"> Zur&uuml;ck </a>
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
</div>

<%--<script src="libs/jquery/jquery.min.js"></script>
<script src="libs/bootstrap/js/bootstrap.min.js"></script>
<script src="js/Sidebar-Menu.js"></script>--%>

</body>

</html>