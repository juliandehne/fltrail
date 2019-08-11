<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>


<script id="portfolioEntryTemplate" type="text/x-jsrender">
    <div></div>

    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        {{for submissionList}}
            <div class="panel panel-default">
                <div>
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-{{:id}}" aria-expanded="false" class="collapsed" aria-controls="collapse-{{:id}}">
                        <div class="btn btn-primary panel-heading" role="tab" id="heading-{{:id}}">
                            <div class="iconGroup">
                                {{if editable}} <button id="editButton" class="btn-primary iconItem" onClick='editButtonPressed("{{:#getIndex()}}")'>{{/if}}<i class="fas fa-edit iconItem {{if !editable}} invisible {{/if}}"></i></button>
                                <i class="fas fa-chevron-down"></i>
                            </div>
                            <div class="panel-title pointer">
                                <h4 class="creator-info uppercase"> {{:creator}} </h4>
                                <h5 class="date-info"> {{:timestampDateTimeFormat}}</h5>
                                <div class="row">
                                    <div id="editor-submission-{{:#getIndex()}}"></div>
                                    {{:#root.data.scriptBegin}}
                                        new Quill('#editor-submission-{{:#getIndex()}}', {
                                            theme: 'snow',
                                            readOnly: true,
                                            "modules": {
                                                "toolbar": false
                                            }
                                        }).setContents({{:text}});
                                    {{:#root.data.scriptEnd}}
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div id="collapse-{{:id}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-{{:id}}">
                    <div class="panel-body">
                        <div class="well">
                            <div class="row">
                                <div class="flex-container">
                                    <h3 class="new-comment"> Neuer Kommentar </h3>
                                    <button class="btn btn-primary save-button" onClick='saveComment("{{:#getIndex()}}")'>Speichern</button>
                                </div>
                            </div>
                            <div class="row">

                                <div id="editor-{{:id}}-new-comment"></div>
                                {{:#root.data.scriptBegin}}
                                    quillNewComment[{{:#getIndex()}}] = new Quill('#editor-{{:id}}-new-comment', {
                                    theme: 'snow',
                                    readOnly: false,
                                    "modules": {
                                        "toolbar": true
                                    }
                                    });
                                {{:#root.data.scriptEnd}}
                            </div>
                        </div>
                        {{for contributionFeedback}}
                            <div class="well">
                                <h4 class="uppercase creator-info"> {{:creator}} </h4>
                                <h5 class="date-info"> {{:timestampDateTimeFormat}}</h5>
                                <div class="row">
                                    <div id="editor-{{:id}}"></div>
                                    {{:#root.data.scriptBegin}}
                                        new Quill('#editor-{{:id}}', {
                                        theme: 'snow',
                                        readOnly: true,
                                        "modules": {
                                            "toolbar": false
                                        }
                                        }).setContents({{:text}});
                                    {{:#root.data.scriptEnd}}
                                </div>
                            </div>
                        {{/for}}
                     </div>
                </div>
            </div>
        {{/for}}
    </div>

</script>