<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Auswahl für Bewertung</title>

    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
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
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>


    <main>


        <div class="row group">

            <div class="col span_2_of_2">
                <h3> E-Portfolio-Einträge für Bewertung einreichen </h3>
                <div id="assessmentTemplateResult"></div>
                <script id="assessmentTemplate" type="text/x-jsrender">
                    <div/>
                    {{if data.length === 0}}
                        <h2>Keine Gruppeneinträge gefunden.</h2>
                    {{else}}
                        <p>Klicken Sie auf die E-Portfolio-Einträge, die Sie von Ihren Eintrgägen für die Gruppe zur Bewertung einreichen möchten.</p>
                        <p>Es werden Einträge angezeigt, die Sie für die Gruppe oder öffentlich freigegeben haben</p>
                        <div class="list-group" id="list-tab" role="tablist">
                            {{for data}}
                                <div class="row">
                                    <label for="list-item-{{:#index}}">{{:#index + 1}}. Eintrag</label>
                                    <a class="list-group-item list-group-item-action pointer" id="list-item-{{:#index}}" onClick='clickItem("{{:#index}}")' role="tab">
                                        <div id="editor-{{:id}}"></div>
                                    </a>
                                    {{:#root.data.extraData.scriptBegin}}
                                        new Quill('#editor-{{:id}}', {
                                            theme: 'snow',
                                            readOnly: true,
                                            "modules": {
                                                "toolbar": false
                                            }
                                        }).setContents({{:text}});
                                    {{:#root.data.extraData.scriptEnd}}
                                </div>
                            {{/for}}
                        </div>
                    {{/if}}
                    <button type="button" onclick="save()" class="btn btn-primary pull-right" id="saveButton">{{:extraData.buttonText}}</button>
                    {{if data.length === 0}}
                         <button type="button" onclick="redirectToPortfolio()" class="btn btn-primary pull-left" id="portfolioButton">Gehe zu Portfolio</button>
                    {{/if}}

                </script>
                <div hidden id="editor"></div>
                <jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
                    <jsp:param name="readOnly" value="true"/>
                </jsp:include>
            </div>
        </div>
    </main>
</div> <!-- flex wrapper -->


<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>