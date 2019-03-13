<%@ page contentType="text/html;charset=UTF-8" %>

<script id="groupTemplate" type="text/x-jQuery-tmpl">

        <div style="" class="grouplists" id="${groupName}">
         <ul class="complex-list">
            <li class="label">
                <div type="button" class="list-group-item list-group-item-action">${groupName}</div>
             </li>

             {{each groupMember}}
                <li>
                 <div type="button" name="student" class="list-group-item list-group-item-action">
                 <span>name: ${name}</span>
                <p name="userEmail">E-mail: ${email}</p>
                {{if discordid}}
                 <p name="discordId"> discordId: ${discordid}</p>
                 {{/if}}
                </div>
                </li>

             {{/each}}
               <li>
                 <p name="chatRoomId" hidden>${chatRoomId}</p>
                </li>
            </ul>
        </div>


</script>

<main class="groups-manual">
    <div class="row group">
        <div id="titleHolder"></div>
        <script type="text/x-jQuery-tmpl" id="titleTemplate">
            <h2 id="groupsHeadline">${computedGroups}</h2>
        </script>
        <div class="col span_content span_2_of_2">
            <div id="noGroupHolder"></div>
            <script type="text/x-jQuery-tmpl" id="noGroupTemplate">
                <div class="alert alert-warning" id="noGroupsYet" style="display: block">
                <div style="display:block">
                    ${noGroupsYet}
                    <p id="participantsMissing">${participantsMissing}</p>
                    ${comeBackAfterMail}
                </div>
                <div style="display:flex">
                    <input name="clpText" value="" readonly class="form-control-plaintext">
                    <button onclick="clpSet();" class="btn btn-secondary"><i class="far fa-clipboard"></i></button>
                </div>
            </div>
            </script>
            <div style="display: flex">
                <div style="display:block">
                    <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject">

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>