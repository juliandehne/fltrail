<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>fltrail</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/createQuiz.js"></script>

    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">

</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1>Projekt1</h1>
            <a href="#"><span class="glyphicon glyphicon-envelope"
                              style="font-size:27px;margin-top:-17px;margin-left:600px;"></span></a>
            <a href="#"><span class="glyphicon glyphicon-cog"
                              style="font-size:29px;margin-left:5px;margin-top:-25px;"></span></a>
        </div>
        <div style="margin-left:50px;">
            <div>
            <input placeholder="Ihre Frage"><!--todo: remember to cut out whitespace and signs (?.,;)-->
            </div>
            <div><label><input type="radio" name="type">Schwierigkeit 3</label></div>
            <div><label><input type="radio" name="type">Schwierigkeit 2</label></div>
            <div><label><input type="radio" name="type">Schwierigkeit 1</label></div>
            <div><input placeholder="korrekte Antwort"><button> + </button><button> - </button></div>
            <div><input placeholder="inkorrekte Antwort"><button> + </button><button> - </button></div>
            <button id="save">speichern</button>
        </div>
    </div>
</div>

</body>
</html>
