let ws;

function connect(targetId, targetCategory) {
    let host = document.location.host;

    ws = new WebSocket("ws://" + host + "/gemeinsamforschen/ws/annotation/" + targetId + "/" + targetCategory);

    ws.onmessage = function (e) {
        let message = JSON.parse(e.data);

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
    let json = JSON.stringify({
        "type": type,
        "annotationId": annotationId
    });

    ws.send(json);
}