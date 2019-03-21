
let context;
let projectName;

function showNoParticipantsMessage() {
    $('#NoParticipantsInfo').show();

    $('#groupsInProject').hide();
    $('.groups-manual').hide();
}

/*function hideNoParticipantsMessage() {
    $('#eMailVerified').hide();
    $('#NoParticipantsInfo').hide();
}*/

function showGroups() {
    $('#groupsInProject').show();
    $('.groups-manual').show();

}

$(document).ready(function () {
    $('#eMailVerified').hide();
    context = getQueryVariable("context");
    projectName = context;

    $('#btnBuildGroups').on('click', function () {

        $('#eMailVerified').show();

        let password = $('#password').val().trim();
        //if (true){
        let encodedPass = context.hashCode();
        if (encodedPass == password) {
            $('#wrongAuthentication').hide();
            $('#authenticationPanel').hide();

            getParticipantsNeeded1(function (projectStatus) {
                //let groupsFormed = projectStatus.groupsFormed;
                if (projectStatus.participants < projectStatus.participantsNeeded) {
                    showNoParticipantsMessage();
                } else {
                    let isAutomated = projectStatus.automated;
                    if (!isAutomated) {
                        initializeOrGetGroups(projectName, function (groups) {
                            groupsToTemplate(groups, function (done) {
                                showGroups();
                            });
                        });
                    }
                }
            });
        } else {
            $('#wrongAuthentication').show();
        }
    });
});

String.prototype.hashCode = function () {
    let hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};


