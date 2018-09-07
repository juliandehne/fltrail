<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>

    <!-- css - annotationStyle -->
    <link rel="stylesheet" type="text/css" href="../assets/css/annotationStyle.css">
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet" type="text/css" />

    <!-- js - jQuery validation plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.js" type="text/javascript"></script>
    <!-- js - scrollTo -->
    <script src="//cdn.jsdelivr.net/npm/jquery.scrollto@2.1.2/jquery.scrollTo.min.js"></script>
    <!-- js - utility -->
    <script src="../assets/js/utility.js"></script>
    <!-- js - annotation websocket script -->
    <script src="../assets/js/annotationWebsocket.js"></script>
    <!-- js - annotation REST script -->
    <script src="../assets/js/annotationRest.js"></script>
    <!-- js - unstructuredRest -->
    <script src="../assets/js/unstructuredRest.js"></script>
    <!-- js - annotationScript -->
    <script src="../assets/js/annotationScript.js"></script>
</head>

<body>
    <div id="wrapper" class="full-height">
        <menu:menu></menu:menu>
        <div class="page-content-wrapper full-height">
            <div class="container-fluid full-height">
                <div class="container-fluid-content">
                    <div class="flex">
                        <headLine:headLine/>
                    </div>
                    <div class="content-mainpage">
                        <div class="leftcolumn">
                            <div class="leftcontent">
                                <div class="leftcontent-text context-menu-one" id="documentText"></div>
                                <div class="leftcontent-buttons">
                                    <div class="leftcontent-buttons-next">
                                        <button id="btnContinue" type="button" class="btn btn-secondary">Weiter</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="rightcolumn">
                            <div class="rightcontent">
                                <ol id="annotations">
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- annotation create modal -->
        <div id="annotation-create-modal" class="modal fade" role="dialog">
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
                                <textarea class="form-control resize-vertical" id="annotation-form-comment" name="comment"></textarea>
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
        <div id="annotation-edit-modal" class="modal fade" role="dialog">
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
                                <label for="annotation-form-title" class="col-form-label">Titel:</label>
                                <input type="text" class="form-control" id="annotation-edit-form-title" name="title">
                            </div>
                            <div class="form-group">
                                <label for="annotation-form-comment" class="col-form-label">Kommentar:</label>
                                <textarea class="form-control resize-vertical" id="annotation-edit-form-comment" name="comment"></textarea>
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
    </div>
</body>

</html>
