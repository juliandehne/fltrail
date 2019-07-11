<%@ page import="com.google.common.base.Strings" %>
<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<%
    TagUtilities tu = new TagUtilities();
    String fileRole = tu.getParamterFromQuery("fileRole", request);
    if (fileRole == null) {
        fileRole = "Unbekannt";
    }
    String personalString = tu.getParamterFromQuery("personal", request);
    if (Strings.isNullOrEmpty(personalString)) {
        personalString = "false";
    }
    String fullSubmissionId = tu.getParamterFromQuery("fullSubmissionId", request);
    if (Strings.isNullOrEmpty(fullSubmissionId)) {
        fullSubmissionId = "";
    }
%>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>
    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <!-- css - upload-unstructured -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-annotation.css">
    <link rel="stylesheet" type="text/css" href="css/annotationColorTheme.css">
    <link rel="stylesheet" type="text/css" href="../taglibs/css/unstructured-upload.css">
    <link rel="stylesheet" type="text/css" href="../taglibs/css/visibilityButton.css">
    <!-- js - unstructuredUpload -->
    <script src="../taglibs/js/unstructuredUpload.js"></script>
    <!-- js - unstructuredRest -->
    <script src="../taglibs/js/unstructuredRest.js"></script>

    <script src="../taglibs/js/visibilityButton.js"></script>
    <script src="js/upload-unstructured-dossier.js"></script>

    <script src="../taglibs/js/annotationUtils.js"></script>
    <script src="../taglibs/js/apiClient/reflectionQuestionClient.js"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>


</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- back-->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>
    <main>
        <div class="row group">
            <div class="col span_content span_l_of_2">
                <div id="headerTemplateResult"></div>
                <!-- TODO: rebuild as one template-->
                <script id="headerTemplate" type="text/x-jsrender">
                    <div></div>
                    <h1>{{:header}}</h1>
                </script>
                <div id="visibilityTemplateResult"></div>
                <script id="visibilityTemplate" type="text/x-jsrender">
                    <div></div>
                    {{if fileRole == "Portfolio"}}
                        <div class="dropdown">
                            <button class="dropbtn btn btn-primary" onclick="dropDownClick()">Sichtbarkeit: {{:currentVisibility.buttonText}}
                                <i class="fa fa-caret-down"></i>
                            </button>
                            <div class="dropdown-content" id="myDropdown">
                                {{for possibleVisibilities}}
                                    <a id={{:name}} onclick='changeButtonText("{{:name}}")'>{{:buttonText}}</a>
                                {{/for}}
                            </div>
                        </div>
                    {{/if}}
                </script>
                <div id="reflectionQuestionTemplateResult"></div>
                <script id="reflectionQuestionTemplate" type="text/x-jsrender">
                    <div></div>
                    {{if fileRole == "Reflection_Question"}}
                        <h2> Frage {{:currentReflectionQuestionCounter}} von {{:totalQuestions}}: {{:question}} </h2>
                    {{/if}}

                </script>
                <br>
                <div class="upload-text" id="documentText">
                    <label for="ownTitle">Titel</label>
                    <input id="ownTitle" size="30" style="font-size: large; margin-bottom: 10px;"
                           placeholder="mein Titel">
                    <label for="editor">Texteingabe</label>
                    <div id="editor"></div>
                </div>

                <div class="document-text-buttons">
                    <%--<button type="button" class="btn btn-secondary document-text-buttons-back" id="btnBack">Zurück
                    </button>--%>
                    <button type="button" class="btn btn-primary document-text-buttons-next" id="btnSave">Speichern
                    </button>
                </div>
            </div>
            <div id="annotationTemplateResult"></div>
            <script id="annotationTemplate" type="text/x-jsrender">
                <div/>
                {{if fileRole == "Dossier"}}
                    <div class="col span span_s_of_2">
                        <div class="infobox dossier">
                            <p>Erstellen Sie ein Dossier
                                <a data-toggle='collapse' href='#whatIs' role='button'
                                   aria-expanded='false' aria-controls='whatIs'>
                                    <i class='fas fa-question'></i>
                                </a>
                                mit den folgenden Kategorien.
                            </p>
                            <div class='collapse' id='whatIs'>
                                <div class='card card-body'>
                                    Ein Dossier ist eine Aktensammlung für Ihr Projekt. Der Dozent hat dabei
                                    festelegt, dass die rechts sichtbaren Kategorien mindestens mit enthalten sein müssen.
                                    Nachdem Sie hier all ihre Textbausteine verfasst haben, kann ihre Gruppe ihren Beitrag
                                    lesen und editieren. Dieser Vorgang endet, wenn ein Mitglied ihrer Gruppe mit Hilfe
                                    der nächsten Aufgabe den Textbausteinen die Kategorien zuordnet.
                                </div>
                            </div>
                        </div>
                        <ol id="annotations">
                            {{for categories}}
                                <li class="spacing">
                                <div class="row group">
                                    <div id="{{>nameLower}}" class="category-card not-added col span_content">
                                        <p>{{>name}}</p>
                                    </div>
                                </div>
                                </li>
                            {{/for}}
                        </ol>
                    </div>
                {{/if}}


            </script>

            <div style="clear:left"></div>
            <%--    <div class="col span_chat">
                    <chat:chatWindow orientation="right" scope="project"/>
                    <chat:chatWindow orientation="right" scope="group"/>
                </div>--%>
        </div> <!-- flex wrapper -->
    </main>
    <jsp:include page="../taglibs/footer.jsp"/>
    <jsp:include page="../taglibs/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="false"/>
    </jsp:include>

    <p id="fullSubmissionId" hidden><%=tu.printMe(fullSubmissionId)%>
    </p>
    <p id="fileRole" hidden><%= tu.printMe(fileRole)%>
    </p>
    <p id="personal" hidden><%= tu.printMe(personalString)%>
    </p>
</body>

</html>
