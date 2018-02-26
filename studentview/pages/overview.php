<?php
include_once '../database/config.php';

if (!isset( $_GET['token'])) {
    header("Location: ../index.php");
}

$query = "SELECT (`name`) from users u where u.token = '". $_GET['token']."';";
//echo $query;
//echo $db->query($query);
$result = mysqli_query($db, $query);
$resultObj = mysqli_fetch_object($result);
$userName = $resultObj->name;
?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>dozent-view-alternativ slider</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/fonts/ionicons.min.css">
    <link rel="stylesheet" href="../assets/css/Contact-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/Customizable-Carousel-swipe-enabled.css">
    <link rel="stylesheet" href="../assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/MUSA_panel-table.css">
    <link rel="stylesheet" href="../assets/css/MUSA_panel-table1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu1.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet'
          type='text/css'>

    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Customizable-Carousel-swipe-enabled.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>
</head>

<body>

<p id="user" hidden><?php echo $userName; ?></p>

<div id="wrapper" style="margin:0px;">
    <div id="sidebar-wrapper" style="width:190px;">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tab-Teilnehmer" role="tab" data-toggle="tab">Teilnehmer </a></li>
            <li><a href="#tab-Leiter" role="tab" data-toggle="tab">Leiter </a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane" role="tabpanel" id="tab-Teilnehmer">
                <ul class="sidebar-nav" style="width:200px;margin-top:50px;">
                    <li> <h3 style="color:white;"> <?php echo $userName; ?> </h3> </li>
                    <li style="width:146px;"> <a href="Projekte.html" style="margin-top:32px;width:200px;">Projekte</a></li>
                    <li style="width:146px;"> <a href="MeineGruppen.html" style="margin-top:32px;width:200px;">Meine Gruppen</a></li>
                    <li style="width:146px;"> <a href="../index.php" style="margin-top:134px;width:200px;">Logout </a></li>
                </ul>
            </div>
            <div class="tab-pane active" role="tabpanel" id="tab-Leiter">
                <ul class="sidebar-nav" style="width:200px;margin-top:50px;">
                    <li> <h3 style="color:white;"> <?php echo $userName; ?> </h3> </li>
                    <li style="width:146px;"> <a href="neuesProjekt.html" style="margin-top:32px;width:200px;">neues Projekt</a></li>
                    <li style="width:146px;"> <a href="overview.html" style="margin-top:32px;width:200px;">Übersicht</a></li>
                    <li style="width:146px;"> <a href="../index.php" style="margin-top:134px;width:200px;">Logout </a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div>
                        <p></p>
                        <p>Hier können Sie ihre Projekte, deren Gruppen und Studenten einsehen. Dafür klicken sie auf das Projekt / die Gruppe, die sie sehen wollen.<br>
                            Zurück kommen sie mit den buttons links unten.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="Div_Promo_Carousel" class="carousel slide" data-ride="" data-interval=false style="width:728px">
        <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
                <div class="container">
                    <div class="row">


                        <div class="col-md-10 col-md-offset-1">

                            <div class="panel panel-default panel-table" style="width:550px">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col col-xs-6">
                                            <h3 class="panel-title">Projekte</h3>
                                            <p><?php echo $userName; ?>/</p>
                                        </div>
                                        <div class="col col-xs-6 text-right">
                                            <button type="button" class="btn btn-sm btn-primary btn-create">erstelle
                                                neuese
                                                Projekt
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <table class="table table-striped table-bordered table-list" style="width:700px">
                                        <thead>
                                        <tr>
                                            <th><em class="fa fa-cog"></em></th>
                                            <th class="hidden-xs">Projekt</th>
                                            <th>Tags</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr href="#Div_Promo_Carousel" role="button" data-slide="next"
                                            style="cursor:pointer;">
                                            <td align="center">
                                                <a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                                                <a class="btn btn-danger"><em class="fa fa-trash"></em></a>
                                            </td>
                                            <td class="hidden-xs">Projekt1</td>
                                            <td>Tag1, Tag2, Tag3, Tag4, Tag5</td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                                                <a class="btn btn-danger"><em class="fa fa-trash"></em></a>
                                            </td>
                                            <td class="hidden-xs">Projekt2</td>
                                            <td>Tag1, Tag2, Tag3, Tag4, Tag5</td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                                                <a class="btn btn-danger"><em class="fa fa-trash"></em></a>
                                            </td>
                                            <td class="hidden-xs">Projekt3</td>
                                            <td>Tag1, Tag2, Tag3, Tag4, Tag5</td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <div class="panel-footer">
                                    <div class="row">
                                        <div class="col col-xs-4">Projekte
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="container">
                    <div class="row">

                        <p></p>
                        <p>Hier können Sie ihre Projekte, deren Gruppen und Studenten einsehen.</p>
                        <p></p>
                        <p></p>

                        <div class="col-md-10 col-md-offset-1">

                            <div class="panel panel-default panel-table" style="width:550px">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col col-xs-6">
                                            <h3 class="panel-title">Gruppen</h3>
                                            <p id="gruppe"><?php echo $userName; ?>/Projekt1/</p>
                                        </div>
                                        <div class="col col-xs-6 text-right">
                                            <button type="button" class="btn btn-sm btn-primary btn-create">erstelle
                                                neuese
                                                Projekt
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <table class="table table-striped table-bordered table-list">
                                        <thead>
                                        <tr>
                                            <th><em class="fa fa-cog"></em></th>
                                            <th class="hidden-xs">Gruppen</th>
                                            <th>Studenten</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr href="#Div_Promo_Carousel" role="button" data-slide="next"
                                            style="cursor:pointer;">
                                            <td align="center">
                                                <a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                                                <a class="btn btn-danger"><em class="fa fa-trash"></em></a>
                                            </td>
                                            <td class="hidden-xs">Gruppe1</td>
                                            <td>Student1, Student2, Student3</td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                                                <a class="btn btn-danger"><em class="fa fa-trash"></em></a>
                                            </td>
                                            <td class="hidden-xs">Gruppe2</td>
                                            <td>Student4, Student5, Student6, Student7</td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <div class="panel-footer">
                                    <div class="row">
                                        <div class="col col-xs-4"><a href="#Div_Promo_Carousel" role="button"
                                                                     data-slide="prev">Projekte</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="container">
                    <div class="row">

                        <p></p>
                        <p>Hier können Sie ihre Projekte, deren Gruppen und Studenten einsehen.</p>
                        <p></p>
                        <p></p>

                        <div class="col-md-10 col-md-offset-1">

                            <div class="panel panel-default panel-table" style="width:550px">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col col-xs-6">
                                            <h3 class="panel-title">Studenten</h3>
                                            <p id="student"><?php echo $userName; ?>/Projekt1/Gruppe1/</p>
                                        </div>
                                        <div class="col col-xs-6 text-right">
                                            <button type="button" class="btn btn-sm btn-primary btn-create">Create New
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <table class="table table-striped table-bordered table-list">
                                        <thead>
                                        <tr>
                                            <th><em class="fa fa-cog"></em></th>
                                            <th class="hidden-xs">Student</th>
                                            <th>E-Mail</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td align="center">
                                                <a class="btn btn-default"><em class="fa fa-pencil"></em></a>
                                                <a class="btn btn-danger"><em class="fa fa-trash"></em></a>
                                            </td>
                                            <td>Student1</td>
                                            <td>Student1@uniexample.com</td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <div class="panel-footer">
                                    <div class="row">
                                        <div class="col col-xs-4"><a href="#Div_Promo_Carousel" role="button"
                                                                     data-slide="prev">Gruppen</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>