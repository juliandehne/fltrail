<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>


<!DOCTYPE html>
<html>


<head>
    <%--<jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>--%>

    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
    <title>Dossier bearbeiten</title>

    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>
    <!-- css - annotationStyle -->
    <link rel="stylesheet" type="text/css" href="css/annotationStyle.css">
    <link rel="stylesheet" type="text/css" href="css/annotationColorTheme.css">
        <link rel="stylesheet" type="text/css" href="../taglibs/css/unstructured-upload.css">

        <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="../libs/jquery/jqueryContextMenu.js"
            type="text/javascript"></script>
    <!-- js - scrollTo -->
    <script src="../libs/jquery/jqueryScrollTo.js"></script>
    <!-- js - rangy Core -->
    <script src="../libs/jquery/jqueryRangy.js" type="text/javascript"></script>
    <!-- js - rangy TextRange Module -->
    <script src="../libs/jquery/jqueryRangyTextRange.js" type="text/javascript"></script>
    <!-- js - annotation REST script -->
        <script src="../taglibs/js/unstructuredRest.js"></script>  <!-- ! -->
    <!-- js - feedbackScript -->
        <script src="js/reedit-dossier.js"></script>

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
    <main id="reEdit" class="">
        <div class="row group">
            <div class="col span_2_of_2">
                <h3><span id="fileRole"></span> bearbeiten</h3>
                <div style="width: 100%;height: 50px; position: absolute;">
                    <button onClick="seeFeedBack();" type="button" class="btn btn-primary" title="Feedback sehen"
                            style="float:right;margin-right: 5%;">
                        <i class="fas fa-comments"></i> Feedback ansehen
                    </button>
                </div>
                <div class="upload-text" id="documentText">
                    <label>Fragestellung / Projektaufgabe</label>
                    <input id="ownTitle" size="30" style="font-size: large; margin-bottom: 10px;" readonly/>
                </div>
                <br>
                <label for="editor">Texteingabe</label>
                <div id="editor"></div>
                <div class="document-text-buttons">
                    <div class="document-text-buttons-back">
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                data-target="#exampleModal">
                            <i class="fas fa-arrow-alt-circle-up"></i> veröffentlichen
                        </button>
                    </div>
                    <button type="button" id="btnSave2" class="btn btn-primary document-text-buttons-next">
                        <i class="far fa-save"></i> speichern
                    </button>
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
                                            Ihre Gruppe hat dann nicht nicht mehr die Möglichkeit diese Version des
                                            Dossiers zu überarbeiten.
                                        </div>
                                        <div class="modal-footer">
                                            <%--  <button type="button" class="btn btn-secondary" data-dismiss="modal">schließen
                                              </button>--%>
                                            <button type="button" id="btnSave" class="btn btn-warning">veröffentlichen
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Button trigger modal -->


                        </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="false"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/footer.jsp"/>

    <div style="height: 200px" id="categoryColor" class="hidden-category-field"></div>
</div>
</body>

</html>
