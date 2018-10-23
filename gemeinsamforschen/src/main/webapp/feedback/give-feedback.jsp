<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../journal/css/create-journal.css">
    <script src="js/givepeerfeedback.js"></script>

</head>

<body>
<menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <h2 style="padding-left: 15px"> Schreibe dein Feedback ein!</h2>
                    <div class="line-spacer"></div>
                    <hr />
                    <div style="padding-left: 17px">
                        <h3>Vorschläge für ein gutes Feedback:</h3>
                        <div>1.  Beginne das Feedback mit etwas Positiven</div>
                        <div>2. 	Äußere dein Feedback</div>
                        <div>3. 	Beende das Feedback mit etwas Positiven</div>
                        <div style="height: 10px"></div>
                        <div>Hilfestellungen für Formulierungen: </div>
                        <div style="height: 10px"></div>
                        <div> - das hat mir gut gefallen, weil ...</div>
                        <div> - das habe ich nicht verstanden, weil ...</div>
                        <div> - an diesem Punkt kam ich nicht weiter, weil ...</div>
                        <div> - das könnte man besser machen, z. B. durch ...</div>
                        <div> - dabei habe ich noch eine Idee, z.B. ...</div>

                    </div>
                    <hr>
                    <div>
                        <table>
                            <tr>
                                <td  id="yourContent">

                                    <form style="padding-left: 20px" id= "journalform" method="POST" action="../rest/peerfeedback/save">

                                        <input type="hidden" id="student" name="student">
                                        <input type="hidden" id="project" name="project">
                                        <input type="hidden" id="feedbackid" name="id">
                                        <input type="hidden" id="rec" name="rec">
                                        <input type="hidden" id="sender" name="sender">
                                        <input type="hidden" id="timestamp" name="timestamp">
                                        <input type="hidden" id="category" name="cat">
                                        <input type="hidden" id="filename" name="filename">
                                        <input type="hidden" id="zsm" name="zsm">


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
                                        <div style="height: 10px"></div>
                                        <div class="journal-form-category">
                                            Feedbackempfänger:
                                            <select name="reciever" id="reciever" form="journalform"> <%--form="journalform"--%>
                                            </select>
                                        </div>

                                        <div class="journal-form-container">

                                            <div class="journal-form-editor" style="width: 150%">
                                                <textarea id="editor" name="text" form="journalform"></textarea> <%--form="journalform"--%>
                                            </div>

                                            <div class="journal-form-buttons">
                                                <button id="backLink" class="btn btn-default btn-sm"> Zur&uuml;ck </button>
                                                <button id="sub" class="btn btn-default btn-sm"> Speichern </button>
                                            </div>
                                        </div>
                                    </form>


                                </td>
                            </tr>
                        </table>
                    </div>
                </tr>
                </td>


</tr>
</table>
</div>
</div>
</div>
</body>

</html>