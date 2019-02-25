<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>


<!DOCTYPE html>
<html>


<head>
    <%--<jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>--%>

    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- css - annotationStyle -->
    <link rel="stylesheet" type="text/css" href="css/annotationStyle.css">
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>

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
    <!-- js - annotation websocket script -->
    <script src="js/annotationWebsocket.js"></script>
    <!-- js - annotation REST script -->
    <script src="js/annotationRest.js"></script>
    <!-- js - unstructuredRest -->
    <script src="js/unstructuredRest.js"></script>
    <!-- js - annotationScript -->
    <script src="js/annotationScript.js"></script>

</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="row group">
        <div class="col span_l_of_2">

            <h3>Feedback geben: <span id="feedBackTarget"></span></h3>





            <h4 id="categoryHeadline"></h4>
            <input type="text" id="annotation-search" onkeyup="searchAnnotation()" placeholder="Suchen...">

            <div id="documentText"></div>



        </div><!-- end col -->

        <div class="col span_s_of_2">

            <div class="pagination-holder">
            <button id="btnBack" type="button" class="btn btn-secondary" title="Zurück">&#xf053;</button>

                <span class="current-category">KATEGORIE</span>

                <button id="btnContinue" type="button" class="btn btn-primary" title="weiter">&#xf054;</button>
                <button id="finalize" type="button" class="btn btn-success" title="finanlisieren">&#xf00c;</button>
            </div>
            <div class="infobox dossier" style="clear:left; margin-top: 120px;">
                <p>Markiere Text und klicke mit der rechten Mousetaste, um den ausgewählten Text zu kommentieren oder</p>

            </div>
            <button id="btnWholeCategory" type="button" class="btn btn-primary">kompletten
                Teil kommentieren
            </button>



                <ol id="annotations">
                </ol>

        </div>
    </div> <!-- end row -->



    <!-- annotation create modal -->
    <div id="annotation-create-modal" class="modal" role="dialog">
        <div class="modal-dialog modal-dialog-centered modal-sm">
            <div class="modal-content">

                <!-- modal header -->
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
                        <button id="btnSave" type="button" class="btn btn-success">Speichern</button>
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
                        <button id="btnEdit" type="button" class="btn btn-success">Bearbeiten</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col span_chat">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
    </div>

</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
