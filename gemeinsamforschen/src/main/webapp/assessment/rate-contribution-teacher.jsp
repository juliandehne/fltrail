<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="js/rateContribution.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- go back to tasks -->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>
    <!-- go back to tasks end-->

    <main>
        <div class="row group">
            <div class="col span_content span_1_of_2">
                <h2>Bewerte Gruppe <span id="groupId"></span></h2>
                <div id="listOfContributions">
                </div>
                <script id="contributionTemplate" type="text/x-jQuery-tmpl">
                    <div class="contributionRating" id="${contributionRole}">
                    <h3>${contributionRole}</h3>
                    <h4>${contributionFileName}<a href="${contributionFilePath}"><i class="fas fa-paperclip"></i></a></h4>
                    {{if contributionText != null}}
                    <div id="editor${contributionRole}" class="ql-container ql-snow ql-disabled">
                    </div>
                    {{/if}}
                    <label>(schlecht)5<input type="radio" name="${contributionRole}" value="5"></label>
                    <label><input type="radio" name="${contributionRole}" value="4">    </label>
                    <label><input type="radio" name="${contributionRole}" value="3">    </label>
                    <label><input type="radio" name="${contributionRole}" value="2">    </label>
                    <label><input type="radio" name="${contributionRole}" value="1"> 1 (sehr gut)</label>
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
        </div>
    </main>
</div>
<jsp:include page="../taglibs/footer.jsp"/>

</body>

</html>