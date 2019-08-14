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
    <title>Dossier vollenden</title>
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>
    <!-- css - annotationStyle -->
    <link rel="stylesheet" type="text/css" href="css/annotationStyle.css">
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
    <script src="js/annotationRest.js"></script>
    <!-- js - unstructuredRest -->
        <script src="../taglibs/js/unstructuredRest.js"></script>
    <!-- js - annotationScript -->
    <script src="js/annotationScript.js"></script>

</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den Aufgaben</a>
    </div>
    <main id="seeFeedback" class="">
        <div id="unauthorized" hidden class="alert alert-warning unauthorized">
            <p>
                Gerade wird dieses Dossier von einem Mitglied Ihrer Gruppe bearbeitet.<br>
                Bitte versuchen Sie es in Kürze erneut.
            </p>
        </div>
        <div class="row group">
            <div class="col span_l_of_2">

                <h3>Feedback geben </h3>
                <p>Gib der Gruppe <span id="feedBackTarget"></span> eine Rückmeldung für das Dossier.</p>

                <br>


                    <div class="three_rows">
                        <button id="btnBack" type="button" class="btn btn-primary" title="Zurück">&#xf053;</button>

                        <h4 id="categoryHeadline" class="current-category"> </h4>
                        <button id="btnContinue" type="button" class="btn btn-primary" title="weiter">&#xf054;</button>
                        <button id="finalize" type="button" class="btn btn-primary" title="finalisieren">&#xf00c;
                        </button>
                    </div>



                <button id="btnWholeCategory" type="button" class="btn btn-primary">kompletten
                    Teil kommentieren
                </button>

                <input type="text" id="annotation-search" onkeyup="searchAnnotation()" placeholder="Suchen...">
                <div style="clear:both"></div>
                <div id="editor" class="context-menu-one"></div>

            </div><!-- end col -->

            <div class="col span_s_of_2">

                <div class="infobox dossier" >
                    <p>Markieren Sie Text und klicken Sie mit der rechten Maustaste, um den ausgewählten Text zu
                        kommentieren.</p>

                </div>


                <h4 class="comment-headline">Feedback</h4>
                <ol id="annotations">

                </ol>


            </div> <!-- end row -->


        <!-- annotation create modal -->
        <div id="annotation-create-modal" class="modal" role="dialog">
            <div class="modal-dialog modal-dialog-centered modal-sm">
                <div class="modal-content">

                    <!--  modal header -->
                     <div class="modal-header flex">
                         <h4 class="modal-title flex-one">Annotation</h4>
                         <button type="button" class="close" data-dismiss="modal">&times;</button>
                     </div>

                     <!-- modal body -->
                    <div class="modal-body">
                        <form id="annotation-create-form">
                            <div class="form-group">
                                <label for="annotation-form-title" class="col-form-label">Titel:</label>
                                <input type="text" class="form-control" id="annotation-form-title" name="title">
                            </div>
                            <div class="form-group">
                                <label for="annotation-form-comment" class="col-form-label">Kommentar:</label>
                                <textarea class="form-control resize-vertical" id="annotation-form-comment"
                                          name="comment"></textarea>
                            </div>
                        </form>
                        <!-- modal footer -->
                        <div class="modal-footer">
                            <button id="btnSave" type="button" class="btn btn-primary">Speichern</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- annotation edit modal -->
        <div id="annotation-edit-modal" class="modal" role="dialog">
            <div class="modal-dialog modal-dialog-centered modal-sm">
                <div class="modal-content">

                    <!-- modal header -->
                    <div class="modal-header flex">
                        <h4 class="modal-title flex-one">Annotation bearbeiten</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- modal body -->
                    <div class="modal-body">
                        <form id="annotation-edit-form">
                            <div class="form-group">
                                <label for="annotation-form-title" class="col-form-label">Titel:
                                    <input type="text" class="form-control" id="annotation-edit-form-title"
                                           name="title">
                                </label>
                            </div>
                            <div class="form-group">
                                <label for="annotation-form-comment" class="col-form-label">Kommentar:
                                    <textarea class="form-control resize-vertical" id="annotation-edit-form-comment"
                                              name="comment"></textarea>
                                </label>
                            </div>
                        </form>
                        <!-- modal footer -->
                        <div class="modal-footer">
                            <button id="btnDelete" type="button" class="btn btn-danger">Löschen</button>
                            <button id="btnEdit" type="button" class="btn btn-primary">Bearbeiten</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="hidden-quill" id="hiddenEditor"></div>
        </div><!-- end row group -->
    </main>
    <script>
        const quillTemp = new Quill("#hiddenEditor", {});
    </script>
    <jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="true"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</div>
</body>

</html>
