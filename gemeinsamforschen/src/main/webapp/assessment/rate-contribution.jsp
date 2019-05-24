<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>
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
        <div class="row group">
            <div class="col span_1_of_2">
                <h2>Letzter Schritt im Projekt </h2>
                <h3>Bewerte Gruppe <span id="groupId"></span></h3>

                <div id="listOfContributions">

                </div>
                <script id="contributionTemplate" type="text/x-jQuery-tmpl">
            <div class="contributionRating" id="${contributionRole}">
                <h4>${contributionFileName}<a href="${contributionFilePath}"><i class="fas fa-paperclip"></i></a></h4>
            {{if contributionText!=null}}
            <div id="editor${contributionRole}" class="ql-container ql-snow ql-disabled">

            </div>
            {{/if}}
                <textarea id="${contributionRole}Feedback">
			        meine Bewertung
			    </textarea>
                <label>(sehr gut)5<input type="radio" name="${contributionRole}" value="5"></label>
                <label><input type="radio" name="${contributionRole}" value="4">    </label>
                <label><input type="radio" name="${contributionRole}" value="3">    </label>
                <label><input type="radio" name="${contributionRole}" value="2">    </label>
                <label><input type="radio" name="${contributionRole}" value="1"> 1 (schlecht)</label>
            </div>


                </script>
                <button id="submit" class="btn btn-primary">Feedback hochladen</button>
                <div id="done" class="alert alert-success">
                    <p>Ihr Feedback wurde erfolgreich gespeichert. Vielen Dank.</p>
                </div>
                <div id="missingFeedback" class="alert alert-warning">
                    <p>Stellen sie sicher alle Beiträge der Gruppe bewertet zu haben.</p>
                </div>
            </div>
            <div class="col span_chat">
                <chat:chatWindow orientation="right" scope="project"/>
                <chat:chatWindow orientation="right" scope="group"/>
            </div>
        </div>
    </main>
        <jsp:include page="../taglibs/footer.jsp"/>

</body>

</html>