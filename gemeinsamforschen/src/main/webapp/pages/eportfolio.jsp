<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>muster-gemeinsam-forschen</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/eportfolio.css">
</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId">project1
                <a href="#">
                <span class="glyphicon glyphicon-envelope"
                      style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
                </a>
                <a href="#">
                    <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
                </a></h1>
        </div>
        <div>
            <table>
                <tr>
                    <td  id="yourContent">
                        <h1>E-Portfolio</h1>
                        <div class="journal-description-container">
                            <div class="journal-description-title">
                            </div>
                            <div class="journal-description-edit">
                                <i class="fa fa-pencil" aria-hidden="true"></i>
                            </div>
                            <div class="journal-description-text">
                            </div>
                            <div class="journal-description-group">
                                <h3>Gruppe</h3>
                            </div>
                            <div class="journal-description-links">
                                <h3>Links</h3>

                            </div>

                        </div>

                        <h2>Lernatagebuch</h2>
                        <select>
                            <option>Alle</option>
                            <option>Eigene</option>
                        </select>

                        <a href="createJournal.html">Neu</a>

                        <div class="journal">
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
<script>

    //TODO Get student and project form context

    $(document).ready(function() {
        $.ajax({
            url: "../rest/prejectdescription/0"
        }).then(function(data) {
            $('.journal-description-title').append('<h2>' + data.name + '</h2>');
            $('.journal-description-text').append(data.description);
            for(var link in data.links){
                $('.journal-description-links').append('<a href=' + data.links[link] + '>' + link + '</a><br/>');
            }
            for(var g in data.group){
                $('.journal-description-group').append(data.group[g]+ '<br/>');

            }
        });

        $.ajax({
            url: "../rest/journal//journals/0/0"
        }).then(function(data) {
            for (var journal in data){
                $('.journal').append(
                    '<div class="journal-container"><div class="journal-avatar">' +
                    'getBild'    +
                    '</div><div class="journal-date"> ' +
                    data[journal].creator +
                    '</div><div class="journal-name">' +
                    timestamptToDateString(data[journal].timestamp)  +
                    '</div><div class="journal-category">' +
                    data[journal].category +
                    '</div><div class="journal-edit">' +
                    '<a href="createJournal.html?journal='+ data[journal].id + '"><i class="fa fa-pencil" aria-hidden="true"></i></a>' +
                    ' </div><div class="journal-text">' +
                    data[journal].entry +
                    '</div>')
            }
            console.log(data);

        });

    });

    function timestamptToDateString(timestamp) {
        var date = new Date(timestamp);
        return date.toLocaleString("de-DE");
    }


</script>
</body>

</html>