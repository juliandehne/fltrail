<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://cdn.rawgit.com/showdownjs/showdown/1.8.5/dist/showdown.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="js/rateContribution.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main>
        <jsp:include page="../taglibs/timeLine.jsp"/>
        <div class="col span_content span_l_of_3">
            <h2>Letzter Schritt im Projekt </h2>
            <div id="listOfContributions">

            </div>

            <script id="contributionTemplate" type="text/x-jQuery-tmpl">
            <div class="contributionRating" id="${contributionName}">
            ${contributionName}
            {{if contributionText}}
                <p>${contributionText}</p>
            {{/if}}
                Die meisten Menschen sind bereit zu lernen, aber nur die wenigsten,
                sich belehren zu lassen.

                <textarea id="${contributionName}Feedback">
			        meine Bewertung
			    </textarea>
                <label>(sehr gut)5<input type="radio" name="${contributionName}" value="5"></label>
                <label><input type="radio" name="${contributionName}" value="4">    </label>
                <label><input type="radio" name="${contributionName}" value="3">    </label>
                <label><input type="radio" name="${contributionName}" value="2">    </label>
                <label><input type="radio" name="${contributionName}" value="1"> 1 (schlecht)</label>
            </div>

            </script>

            <p id="groupId" hidden>Hier steht jetzt das richtige</p>
            <table>
                <tr>
                    <td id="yourContent">
                        <table class="table-striped peerStudent"
                               style="width:100%;border:1px solid; margin:auto;" id="2">
                            <tr>
                                <td align="center">
                                    <h3>Gruppe 4</h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="contributionRating" id="Dossier">
                                        Dossier:
                                        Hier sollte das Dossier stehen und herunterladbar sein.
                                        Die meisten Menschen sind bereit zu lernen, aber nur die wenigsten,
                                        sich belehren zu lassen.
                                        <textarea id="dossierFeedback">
				                        meine Bewertung
			                        </textarea>
                                        <label><input type="radio" name="dossier" value="5">Perfekt</label>
                                        <label><input type="radio" name="dossier" value="4">Makellos</label>
                                        <label><input type="radio" name="dossier" value="3">regulär</label>
                                        <label><input type="radio" name="dossier" value="2">Makelhaft</label>
                                        <label><input type="radio" name="dossier" value="1">Lädiert</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="contributionRating" id="research">
                                        Präsentation: <a href="#"><i class="fa fa-paperclip"></i></a>
                                        <textarea id="presentationFeedback">
				                        meine Bewertung
			                        </textarea>
                                        <label><input type="radio" name="research" value="5">Perfekt</label>
                                        <label><input type="radio" name="research" value="4">Makellos</label>
                                        <label><input type="radio" name="research" value="3">regulär</label>
                                        <label><input type="radio" name="research" value="2">Makelhaft</label>
                                        <label><input type="radio" name="research" value="1">Lädiert</label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <button id="submit" class="btn btn-success">Feedback hochladen</button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="col span_chat">
            <chat:chatWindow orientation="right" scope="project"/>
            <chat:chatWindow orientation="right" scope="group"/>
        </div>
        <jsp:include page="../taglibs/footer.jsp"/>
    </main>
</body>

</html>