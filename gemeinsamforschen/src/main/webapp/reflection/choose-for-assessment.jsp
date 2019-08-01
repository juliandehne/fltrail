<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>
    <!-- jsrender -->
    <link rel="stylesheet" type="text/css" href="css/choose-for-assessment.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
    <script src="../taglibs/js/apiClient/reflectionClientGeneral.js"></script>
    <script src="../libs/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/choose-for-assessment.js"></script>
    <script src="../taglibs/js/quill/quillArrayEntryObject.js"></script>
    <script src="../taglibs/js/quill/quillJsObject.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main>
        <div class="backlink">
            <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
                Aufgaben</i></a>
        </div>

        <div class="group">
            <h1> Reflexionsfragen für Bewertung aussuchen </h1>

            <div id="assessmentTemplateResult"></div>
            <script id="assessmentTemplate" type="text/x-jsrender">
                <div/>
                <div class="col">
                    <b>Information: Klicke die Reflexionsfragen an, die du zur Bewertung einreichen möchtest.</b>
                    <div class="list-group" id="list-tab" role="tablist">
                        {{for data}}
                            <div class="row">
                                <h3>{{:question.question}}</h3>
                                <a class="list-group-item list-group-item-action pointer" id="list-item-{{:#index}}" onClick='clickReflectionQuestion("{{:#index}}")' role="tab">
                                    <div id="editor-{{:question.id}}"></div>
                                </a>
                                {{:#root.data.extraData.scriptBegin}}
                                new Quill('#editor-{{:question.id}}', {
                                    theme: 'snow',
                                    readOnly: true,
                                    "modules": {
                                        "toolbar": false
                                    }
                                }).setContents({{:answer.text}});
                                {{:#root.data.extraData.scriptEnd}}
                            </div>
                        {{/for}}
                    </div>
                    <br/>
                    <div class="row">
                        <button type="button" onclick="save()" class="btn btn-primary pull-right" id="saveButton">Speichern</button>
                    </div>

                </div>
            </script>

        </div>

    </main>
</div> <!-- flex wrapper -->
<jsp:include page="../taglibs/footer.jsp"/>
<div hidden id="editor"></div>
<jsp:include page="../taglibs/quillJsEditor.jsp">
    <jsp:param name="readOnly" value="true"/>
</jsp:include>
</body>

</html>
