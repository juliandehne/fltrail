<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>muster-gemeinsam-forschen</title>

    <!-- css - annotationStyle -->
    <link rel="stylesheet" type="text/css" href="../assets/css/annotationStyle.css">
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
    <!-- css - bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- css - styles -->
    <link rel="stylesheet" href="../assets/css/styles.css">
    <!-- css - font awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
    <!-- css - sidebar -->
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">

    <!-- js - jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <!-- js - bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.js" type="text/javascript"></script>
    <!-- js - utility script -->
    <script src="../assets/js/utility.js"></script>
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
                        <h1>gemeinsam Forschen
                            <a href="#">
                    <span class="glyphicon glyphicon-envelope"
                          style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
                            </a>
                            <a href="#">
                                <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
                            </a>
                        </h1>
                    </div>
                    <div class="content-mainpage">
                        <div class="leftcolumn">
                            <div class="leftcontent">
                                <div class="leftcontent-text context-menu-one" id="documentText">
                                    Style never met and those among great. At no or september sportsmen he perfectly happiness attending. Depending listening delivered off new she procuring satisfied sex existence. Person plenty answer to exeter it if. Law use assistance especially resolution cultivated did out sentiments unsatiable. Way necessary had intention happiness but september delighted his curiosity. Furniture furnished or on strangers neglected remainder engrossed. Shot what able cold new the see hold. Friendly as an betrayed formerly he. Morning because as to society behaved moments. Put ladies design mrs sister was. Play on hill felt john no gate. Am passed figure to marked in. Prosperous middletons is ye inhabiting as assistance me especially. For looking two cousins regular amongst.
                                    Style never met and those among great. At no or september sportsmen he perfectly happiness attending. Depending listening delivered off new she procuring satisfied sex existence. Person plenty answer to exeter it if. Law use assistance especially resolution cultivated did out sentiments unsatiable. Way necessary had intention happiness but september delighted his curiosity. Furniture furnished or on strangers neglected remainder engrossed. Shot what able cold new the see hold. Friendly as an betrayed formerly he. Morning because as to society behaved moments. Put ladies design mrs sister was. Play on hill felt john no gate. Am passed figure to marked in. Prosperous middletons is ye inhabiting as assistance me especially. For looking two cousins regular amongst.
                                </div>
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
                        <form>
                            <div class="form-group">
                                <label for="annotation-form-title" class="col-form-label">Titel:</label>
                                <input type="text" class="form-control" id="annotation-form-title">
                            </div>
                            <div class="form-group">
                                <label for="annotation-form-comment" class="col-form-label">Kommentar:</label>
                                <textarea class="form-control resize-vertical" id="annotation-form-comment"></textarea>
                            </div>
                        </form>
                    </div>

                    <!-- modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Abbrechen</button>
                        <button type="button" class="btn btn-primary">Speichern</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</body>

</html>
