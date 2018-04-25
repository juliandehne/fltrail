/**
 * Created by dehne on 25.04.2018.
 */

function xmlToString(xmlData) {

    var xmlString;
    //IE
    if (window.ActiveXObject) {
        xmlString = xmlData.xml;
    }
    // code for Mozilla, Firefox, Opera, etc.
    else {
        xmlString = (new XMLSerializer()).serializeToString(xmlData);
    }
    return xmlString;
}


$(document).ready(function () {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/gemeinsamforschen/rest/api/munschkin/1",
        dataType: "xml",
        success: function (data) {
            /* handle data here */
            //var munschkin = $.parseXML(data)
            $("#munschkin").append(xmlToString(data));
        },
        error: function (xhr, status) {
            alert("Server down .... ogottogottogottt")
        }
    });

});

