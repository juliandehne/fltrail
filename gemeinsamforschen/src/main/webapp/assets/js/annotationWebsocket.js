var ws;

function connect(targetId) {
    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host  + "/ws/annotation/" + targetId);

    ws.onmessage = function (e) {
        var message = JSON.parse(e.data);
        console.log(message.from)
    };
}

function send(type, annotationId) {
    var json = JSON.stringify({
        "type":type,
        "annotationId":annotationId
    })

    ws.send(json);
}