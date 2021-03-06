<%@ page import="com.google.common.base.Strings" %>
<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
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
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Dossier schreiben</title>
    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <!-- css - upload-unstructured -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-annotation.css">
    <link rel="stylesheet" type="text/css" href="css/annotationColorTheme.css">
    <link rel="stylesheet" type="text/css" href="../taglibs/css/unstructured-upload.css">
    <link rel="stylesheet" type="text/css" href="../taglibs/css/dropDownButton.css">
    <!-- js - unstructuredUpload -->
    <script src="../taglibs/js/unstructuredUpload.js"></script>
    <!-- js - unstructuredRest -->
    <script src="../taglibs/js/unstructuredRest.js"></script>

    <script src="../taglibs/js/dropDownButton.js"></script>
    <script src="js/upload-unstructured-dossier.js"></script>

    <script src="../taglibs/js/annotationUtils.js"></script>
    <script src="../taglibs/js/apiClient/reflectionQuestionClient.js"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>


</head>

<body>

<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- back-->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>
    <main>
        <div id="unauthorized" hidden class="alert alert-warning unauthorized">
            <p>
                Gerade wird dieses Dossier von einem Mitglied Ihrer Gruppe bearbeitet.<br>
                Bitte versuchen Sie es in Kürze erneut.
            </p>
        </div>
        <div class="row group">
            <div class="col span_content span_l_of_2">
                <div id="headerTemplateResult"></div>
                <!-- TODO: rebuild as one template-->
                <script id="headerTemplate" type="text/x-jsrender">
                    <div></div>
                    <h3>{{:header}}</h3>


                </script>
                <div id="visibilityTemplateResult"></div>
                <script id="visibilityTemplate" type="text/x-jsrender">
                    <div></div>
                    {{if fileRole.toUpperCase() == "PORTFOLIO_ENTRY"}}
                        <div class="dropdown fltrailselect">
                            <label for="visibilityDropDown">Sichtbarkeit</label>
                            <button id="visibilityDropDown"class="dropbtn" onclick='dropDownClick("myDropdown")'>{{:currentVisibility.buttonText}}

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
                    {{if fileRole.toUpperCase() == "REFLECTION_QUESTION"}}
                        <h4> Frage {{:currentReflectionQuestionCounter}} von {{:totalQuestions}}: {{:question}} </h4>
                    {{/if}}


                </script>
                <br>
                <div id="editorTitleTemplateResult"></div>
                <script id="editorTitleTemplate" type="text/x-jsrender">
                    <div/>
                    {{if fileRole.toUpperCase() === "DOSSIER" || fileRole.toUpperCase() === "PORTFOLIO_ENTRY"}}
                        <div class="upload-text" id="documentText">
                            <label for="ownTitle">{{:label}}</label>
                            <input id="ownTitle" size="30" style="font-size: large; margin-bottom: 10px;" placeholder="{{:placeholder}}">
                        </div>
                    {{/if}}


                {{if fileRole.toUpperCase() != "REFLECTION_QUESTION"}}
                <label for="editor">Texteingabe</label>
                {{/if}}


                </script>
                <div id="editor"></div>


                <div class="document-text-buttons">
                    <%--<button type="button" class="btn btn-secondary document-text-buttons-back" id="btnBack">Zurück
                    </button>--%>
                    <div id="saveTemplateResult" class="document-text-buttons-back"></div>
                    <script id="saveTemplate" type="text/x-jsrender">
                            <div/>
                            {{if fileRole.toUpperCase() === "DOSSIER"}}
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary"
                                data-toggle="modal" data-target="#exampleModal">
                                    <i class="fas fa-arrow-alt-circle-up"></i> veröffentlichen
                                </button>
                            {{/if}}

                    </script>
                    <div>
                        <!-- Modal -->
                        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                             aria-labelledby="speichernDialog" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="speichernDialog">Finale Version des Dossiers
                                            speichern</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Ihre Gruppe hat dann nicht mehr die Möglichkeit diese Version des
                                        Dossiers zu überarbeiten.
                                    </div>
                                    <div class="modal-footer">
                                        <%--  <button type="button" class="btn btn-secondary" data-dismiss="modal">schließen
                                          </button>--%>
                                        <button type="button" id="btnFinalSave" class="btn btn-warning">final
                                            speichern
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="button" class="btn btn-primary document-text-buttons-next" id="btnSave">
                        <i class="far fa-save"></i> Speichern
                    </button>

                    <div style="display: block">
                        <%--<div style="display: inline-flex;">
                            <label for="finalizeReedit" style="margin-right:5px">
                                <sup>
                                <a data-toggle='collapse' href='#whatIsFirst' role='button'
                                   aria-expanded='false' aria-controls='whatIsFirst'>
                                    <i class='fas fa-question'></i>
                                </a>
                                </sup>
                                Dies ist die erste Version des Gruppendossiers.</label>
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="finalizeReedit" checked>
                                <label class="custom-control-label" for="finalizeReedit">finalisieren</label>
                            </div>
                            &lt;%&ndash;<input id="finalizeReedit" type="checkbox" title="finalisieren" style="margin-top:-2px">&ndash;%&gt;
                        </div>--%>
                        <div class='collapse' id='whatIsFirst'>
                            <div class='card card-body card-whatIs'>
                                Hierzu werden Sie Feedback von anderen Projektteilnehmern bekommen und können es
                                dann erneut bearbeiten.
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div id="annotationTemplateResult"></div>
            <script id="annotationTemplate" type="text/x-jsrender">
                <div/>
                {{if fileRole.toUpperCase() == "DOSSIER"}}
                    <div class="col span span_s_of_2">
                        <div class="infobox dossier">
                            <p>
                                <a data-toggle='collapse' href='#whatIs' role='button'
                                   aria-expanded='false' aria-controls='whatIs'>
                                    <i class='fas fa-question'></i>
                                </a>
                                Erstellen Sie ein Dossier mit den folgenden Kategorien.
                            </p>
                            <div class='collapse' id='whatIs'>
                                <div class='card card-body'>
                                    Ein Dossier ist eine Aktensammlung für Ihr Projekt. Die dozierende Person hat dabei
                                    festgelegt, dass die rechts sichtbaren Kategorien mindestens mit enthalten sein müssen.
                                    Nachdem Sie hier all ihre Textbausteine verfasst haben, können Sie oder ihre Gruppe
                                    ihren Beitrag lesen und editieren. Dieser Vorgang endet, wenn ein Mitglied ihrer
                                    Gruppe mit Hilfe der nächsten Aufgabe den Textbausteinen die Kategorien zuordnet.
                                </div>
                            </div>
                        </div>
                        <ul id="annotations">
                            {{for categories}}
                                <li class="spacing">

                                    <div id="{{>nameLower}}" class="category-card not-added">
                                        <p>{{>name}}</p>
                                    </div>

                                </li>
                            {{/for}}
                        </ul>
                    </div>
                {{/if}}



            </script>

            <div style="clear:left"></div>

        </div>
    </main>


    <jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="false"/>
    </jsp:include>


    <p id="fullSubmissionId" hidden><%=tu.printMe(fullSubmissionId)%>
    </p>
    <p id="fileRole" hidden><%= tu.printMe(fileRole)%>
    </p>
    <p id="personal" hidden><%= tu.printMe(personalString)%>
    </p>
    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>
