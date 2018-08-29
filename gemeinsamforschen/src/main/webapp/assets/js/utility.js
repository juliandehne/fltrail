$(document).ready(function(){
    $('#headLineProject').html($('#projectId').html());
    $('#logout').click(function(){
        //todo: delete cookies / reset session
        document.location="../index.jsp";
    });
    function goBack() {
        window.history.back();
    }
});

function getUserTokenFromUrl() {
    let parts = window.location.search.substr(1).split("&");
    let $_GET = {};
    for (let i = 0; i < parts.length; i++) {
        let temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    return $_GET['token'];

}

function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}
