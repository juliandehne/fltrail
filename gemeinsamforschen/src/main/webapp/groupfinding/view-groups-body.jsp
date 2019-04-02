<%--
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
                <p name="userEmail" class="userEmailField">E-mail: ${email}</p>
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
        <h2 id="Gruppeneinteilung">Gruppeneinteilung</h2>
        <h2 id="groupsHeadline">Computed Groups</h2>
        <div class="col span_content span_2_of_2">
            <div class="alert alert-warning" id="noGroupsYet" style="display: block">
                <div style="display:block">
                    There are no groups built yet.<br>
                    <p id="participantsMissing"></p>
                    Please come back to this page after you get an E-Mail, that groups where built.<br>
                </div>
                <div style="display:flex">
                    <input name="clpText" value="" readonly class="form-control-plaintext">
                    <button onclick="clpSet();" class="btn btn-secondary"><i class="far fa-clipboard"></i></button>
                </div>
            </div>
            <div class="alert alert-warning" id="bisherKeineGruppen">
                Die Gruppen wurden noch nicht gebildet.<br>
                <p id="teilnehmerFehlend"></p>
                Bitte kommen sie zu dieser Seite zurück nachdem sie eine E-Mail diesbezüglich bekommen haben.
                <div style="display:flex; margin-top: 20px;">
                    <input name="clpText" value="" readonly class="form-control-plaintext" size="80">
                    <button onclick="clpSet();" class="btn btn-primary" style="margin-left:10px;"><i
                            class="far fa-clipboard"></i></button>
                </div>
            </div>
            <div style="display: flex">
                <div style="display:block">
                    <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject">

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>--%>
