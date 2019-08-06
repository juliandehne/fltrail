<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>

<script id="portfolioEntryTemplate" type="text/x-jsrender">
                <div></div>
                {{for submissionList}}
                    <br/>
                    <div id="editor-{{:id}}"></div>
                    {{:scriptBegin}}
                    new Quill('#editor-{{:id}}', {
                        theme: 'snow',
                        readOnly: true,
                        "modules": {
                            "toolbar": false
                        }
                    }).setContents({{:text}});
                    {{:scriptEnd}}
                    <h4 class="creation-information">
                    {{if editable}}
                        <a href="../annotation/upload-unstructured-dossier.jsp?projectName={{:projectName}}&fullSubmissionId={{:id}}&fileRole=Portfolio_Entry&personal=true">Editieren</a> -
                    {{/if}}
                    <a class="pointer" onClick='clickedWantToComment("{{:id}}")'>Kommentieren</a> - {{:creator}} - {{:timestampDateTimeFormat}}</h4>
                    <br/>
                    <br/>
                    {{if wantToComment}}
                        <div style="padding-left: 100px;">
                            <h3 class="creation-information"> Neuer Kommentar </h3>
                            <br/>
                            <br/>
                            <br/>
                            <br/>
                            <div id="editor-{{:id}}-new-comment"></div>
                        </div>
                        {{:scriptBegin}}
                        quillNewComment = new Quill('#editor-{{:id}}-new-comment', {
                        theme: 'snow',
                        readOnly: false,
                        "modules": {
                            "toolbar": true
                        }
                    });
                    {{:scriptEnd}}
                    </br>
                    <button class="creation-information btn btn-primary" onClick='saveComment("{{:id}}")'> Speichern </button>
                    <br/>
                    <br/>
                    <br/>
                    {{/if}}
                    {{for contributionFeedback}}
                        <div style="padding-left: 100px;">
                            <div id="editor-{{:id}}"></div>
                        </div>
                        {{:scriptBegin}}
                        new Quill('#editor-{{:id}}', {
                        theme: 'snow',
                        readOnly: true,
                        "modules": {
                            "toolbar": false
                        }
                    }).setContents({{:text}});
                    {{:scriptEnd}}
                    <h4 class="creation-information">{{:userEmail}} - {{:timestampDateTimeFormat}}</h4>
                    <br/>
                    <br/>
                    {{/for}}
                {{/for}}
                {{if error}}
                    <h1>Keine Einträge gefunden</h1>
                {{/if}}
</script>