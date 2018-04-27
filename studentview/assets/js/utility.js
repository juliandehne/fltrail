function getUserTokenFromUrl() {
    var parts = window.location.search.substr(1).split("&");
    var $_GET = {};
    for (var i = 0; i < parts.length; i++) {
        var temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    return $_GET['token'];
}


function blockScreen(){                 //todo: in eigenes File auslagern
    document.getElementById('block-screen').className = "block-screen";
    document.getElementById('loader').className = "loader";
    document.getElementById('wrapper').className = "wrapper-inactive";
}

function deblockScreen(){               //todo: in eigenes File auslagern
    document.getElementById('block-screen').className = "block-screen-inactive";
    document.getElementById('loader').className = "loader-inactive";
    document.getElementById('wrapper').className = "wrapper";
}
