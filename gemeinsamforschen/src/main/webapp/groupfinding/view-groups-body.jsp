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
        <h2 id="Gruppeneinteilung">Gruppeneinteilung</h2>
        <h2 id="groupsHeadline">groups</h2>
        <div class="col span_content span_2_of_2">
            <div class="alert alert-warning" id="noGroupsYet">There are no groups built yet.</div>
            <div class="alert alert-warning" id="bisherKeineGruppen">Die Gruppen wurden noch nicht gebildet.</div>
                <div style="display: flex">
                    <div style="display:block">
                        <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject">

                        </div>
                    </div>
            </div>
        </div>
    </div>
</main>