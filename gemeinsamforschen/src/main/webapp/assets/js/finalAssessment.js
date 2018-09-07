$(document).ready(function () {
    checkAssessementPhase();


    $('#notAllRated').hide();
    $(".carousel").carousel({
        interval: false
    });
    buildTablesForPeers();

    $('#assessThePeer').on("click", function () {
        assessPeer();
    });
});


function buildTablesForPeers() {
    let studentId = $('#user').html().trim();
    let projectId = $('#projectId').html().trim();
        $.ajax({
            url: '../rest/group/project/' + projectId + '/student/' + studentId,
            type: 'GET',
            headers: {
                "Content-Type": "text/javascript",
                "Cache-Control": "no-cache"
            },
            success: function (peers) {
                let div = document.getElementById('peerTable');
                for (let peer = 0; peer < peers.length; peer++) {
                    let tablePeer = document.createElement('DIV');
                    if (peer === 0) {
                        tablePeer.className = "item active";
                    } else {
                        tablePeer.className = "item";
                    }
                    let innerPeerTable = '<table class="table-striped peerStudent" id="' + peers[peer] + '">' +
                        '<tr>' +
                        '<td align="center">' +
                        '<img src="../assets/img/noImg.png" alt="' + peers[peer] + '" style="width:20%;">' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<h3>Verantwortungsbewusstsein</h3>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<label>stark ausgeprägt<input type="radio" value="5" name="responsibility' + peers[peer] + '"></label>' +
                        '<input type="radio" value="4" name="responsibility' + peers[peer] + '">' +
                        '<input type="radio" value="3" name="responsibility' + peers[peer] + '">' +
                        '<input type="radio" value="2" name="responsibility' + peers[peer] + '">' +
                        '<label><input type="radio" value="1" name="responsibility' + peers[peer] + '">ungenügend</label>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<h3>Anteil am Produkt</h3>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<label>großer Anteil<input type="radio" value="5" name="partOfWork' + peers[peer] + '"></label>' +
                        '<input type="radio" value="4" name="partOfWork' + peers[peer] + '">' +
                        '<input type="radio" value="3" name="partOfWork' + peers[peer] + '">' +
                        '<input type="radio" value="2" name="partOfWork' + peers[peer] + '">' +
                        '<label><input type="radio" value="1" name="partOfWork' + peers[peer] + '">geringer Anteil</label>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<h3>Kooperationsbereitschaft</h3>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<label>sehr kooperativ<input type="radio" value="5" name="cooperation' + peers[peer] + '">' +
                        '</label>' +
                        '<input type="radio" value="4" name="cooperation' + peers[peer] + '">' +
                        '<input type="radio" value="3" name="cooperation' + peers[peer] + '">' +
                        '<input type="radio" value="2" name="cooperation' + peers[peer] + '">' +
                        '<label><input type="radio" value="1" name="cooperation' + peers[peer] + '">nicht kooperativ</label>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<h3>Disskusionsfähigkeit</h3>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<label>gut kommuniziert und Meinung vertreten<input type="radio" value="5" name="communication' + peers[peer] + '">' +
                        '</label>' +
                        '<input type="radio" value="4" name="communication' + peers[peer] + '">' +
                        '<input type="radio" value="3" name="communication' + peers[peer] + '">' +
                        '<input type="radio" value="2" name="communication' + peers[peer] + '">' +
                        '<label><input type="radio" value="1" name="communication' + peers[peer] + '">keine Meinung und schlecht kommuniziert</label>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<h3>Selbstständigkeit</h3>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td align="center">' +
                        '<label>selbstständig<input type="radio" value="5" name="autonomous' + peers[peer] + '">' +
                        '</label>' +
                        '<input type="radio" value="4" name="autonomous' + peers[peer] + '">' +
                        '<input type="radio" value="3" name="autonomous' + peers[peer] + '">' +
                        '<input type="radio" value="2" name="autonomous' + peers[peer] + '">' +
                        '<label><input type="radio" value="1" name="autonomous' + peers[peer] + '">abhängig</label>' +
                        '</td>' +
                        '</tr>' +
                        '</table>' +
                        '<div align="center">' +
                        '   <button class="btn btn-primary" id="btnJournal' + peers[peer] + '">' +
                        '   zeige Lernzieltagebuch</button>' +
                        '   <div id="eJournal' + peers[peer] + '">Fasel Blubba Bla</div>' +
                        '</div>';
                    tablePeer.innerHTML = innerPeerTable;
                    div.appendChild(tablePeer);
                    $('#btnJournal' + peers[peer]).on("click", function () {
                        $('#eJournal' + peers[peer]).toggle();
                    });
                }
            },
            error: function () {
            }
        }
    );
}

function assessPeer() {
    let peerStudents = $('.peerStudent');
    ///////initialize variables///////
    let dataP = new Array(peerStudents.length);
    let rateThis = ['responsibility', 'partOfWork', 'cooperation', 'communication', 'autonomous'];

    ///////read values from html///////
    for (let peer = 0; peer < peerStudents.length; peer++) {
        let workRating = {};
        let peerRating = {
            "fromPeer": $('#user').html().trim(),
            "toPeer": peerStudents[peer].id,
            "workRating": {}
        };
        for (let rate = 0; rate < rateThis.length; rate++) {
            let category = rateThis[rate];
            workRating[category] = ($('input[name=' + rateThis[rate] + peerStudents[peer].id + ']:checked').val());
        }

        peerRating.workRating = workRating;
        //////write values in Post-Variable
        dataP[peer] = peerRating;
    }
    for (let peer = 0; peer < dataP.length; peer++) {
        for (let workRating = 0; workRating < rateThis.length; workRating++) {
            if (dataP[peer].workRating[rateThis[workRating]] === undefined) {
                $('#notAllRated').show();
                return;
            }
        }
    }
    let projectId = $('#projectId').html().trim();
    $.ajax({
        url: '../rest/assessments/peerRating/project/' + projectId,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function () {
            location.href = "takeQuiz.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
        },
        error: function (a, b, c) {

        }
    });
}
