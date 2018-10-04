<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>

    <!-- css - unstructured-annotation -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-annotation.css">
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>

    <!-- js - jQuery validation plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"
            type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.js"
            type="text/javascript"></script>
    <!-- js - rangy Core -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-core.js" type="text/javascript"></script>
    <!-- js - rangy TextRange Module -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-textrange.js" type="text/javascript"></script>
    <!-- js - unstructuredRest -->
    <script src="js/unstructuredRest.js"></script>
    <!-- js - unstructuredUpload -->
    <script src="js/unstructuredAnnotation.js"></script>


</head>

<body>
<menu:menu hierarchy="1"/>
<div id="wrapper">
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
                                <div class="leftcontent-buttons-save">
                                    <button id="btnSave" type="button" class="btn btn-secondary">Speichern</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="rightcolumn">
                        <div class="rightcontent">
                            <ol id="annotations">
                                <li class="spacing">
                                    <div id="titel" class="category-card not-added">
                                        <p>Titel</p>
                                    </div>
                                </li>
                                <li class="spacing">
                                    <div id="recherche" class="category-card not-added">
                                        <p>Recherche</p>
                                    </div>
                                </li>
                                <li class="spacing">
                                    <div id="literaturverzeichnis" class="category-card not-added">
                                        <p>Literaturverzeichnis</p>
                                    </div>
                                </li>
                                <li class="spacing">
                                    <div id="forschungsfrage" class="category-card not-added">
                                        <p>Forschungsfrage</p>
                                    </div>
                                </li>
                                <li class="spacing">
                                    <div id="untersuchungskonzept" class="category-card not-added">
                                        <p>Untersuchungskonzept</p>
                                    </div>
                                </li>
                                <li class="spacing">
                                    <div id="methodik" class="category-card not-added">
                                        <p>Methodik</p>
                                    </div>
                                </li>
                                <li class="spacing">
                                    <div id="durchfuehrung" class="category-card not-added">
                                        <p>Durchführung</p>
                                    </div>
                                </li>
                                <li>
                                    <div id="auswertung" class="category-card not-added">
                                        <p>Auswertung</p>
                                    </div>
                                </li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
