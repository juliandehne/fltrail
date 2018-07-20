var ws;

function connect(targetId) {
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host  + "/ws/annotation/" + targetId);

    ws.onmessage = function (e) {
        var message = JSON.parse(e.data);
        console.log(message.from)

        if (message.type === "CREATE") {
            // get annotation from server
            getAnnotation(message.annotationId, function (response) {
                // display annotation
                displayAnnotation(response)
            })
        }
        else if (message.type === "DELETE") {
            // remove annotation from list
            $('#' + message.annotationId).closest('.listelement').remove()
        }
        else if (message.type === "EDIT") {
            getAnnotation(message.annotationId, function (response) {
                editAnnotationValues(response);
            })
        }
    };
}

function send(type, annotationId) {
    var json = JSON.stringify({
        "type":type,
        "annotationId":annotationId
    })

    ws.send(json);
}