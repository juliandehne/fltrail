<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
<link rel="stylesheet" type="text/css" href="../taglibs/css/dropDownButton.css">
<script src="../taglibs/js/dropDownButton.js"></script>
<script src="js/portfolio-shared.js"></script>
<script src="js/docent-shared.js"></script>
<script src="../taglibs/js/apiClient/contributionFeedbackClient.js"></script>


<div class="row group">

    <div id="portfolioTemplateResult"></div>
    <script id="portfolioTemplate" type="text/x-jsrender">
                <div></div>
                <h1>{{:pageTitle}}</h1>
                    {{if possibleButtons.length === 0}}
                        <h2>Keine Einträge gefunden</h2>
                    {{else}}
                        <h4>Wählen Sie, welche Beiträge Sie sehen möchten</h4>
                        <div class="dropdown fltrailselect">
                            <button class="dropbtn" onclick='dropDownClick("myDropdown")'>{{:currentVisibleButtonText}}
                            </button>
                            <div class="dropdown-content" id="myDropdown">
                                {{for possibleButtons}}
                                    <a id={{:name}} onclick='visibilityButtonPressed("{{:name}}")'>{{:name}}</a>
                                {{/for}}
                            </div>
                        </div>
                        <hr class="spacer-nofloat" style="border-color:transparent">
                        <label>Einträge</label>
                        {{include tmpl="#portfolioEntryTemplate"/}}
                    {{/if}}


    </script>
    <jsp:include page="submission-entries-template.jsp"/>
</div>
<!-- flex wrapper -->