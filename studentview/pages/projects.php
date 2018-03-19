<?php
include_once '../database/tokenSetter.php';
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
    <script src="../assets/js/overview.js"></script>
</head>

<body>

<p id="user" hidden><?php echo $userName; ?></p>

<div id="wrapper" style="margin:0px;">
    <?php
    include_once 'menu.php'
    ?>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div>
                        <p></p>
                        <p>Hier können Sie ihre Projekte, deren Gruppen und Studenten einsehen. Dafür klicken sie auf
                            das Projekt / die Gruppe, die sie sehen wollen.<br>
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
                                            <button type="button" class="btn btn-sm btn-primary btn-create"
                                                    onclick="location.href='neuesProjekt.php?token=<?php echo $token ?>'">
                                                erstelle
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
                                        <tbody id="projectTable">

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
                                            <p id="gruppe"><?php echo $userName; ?>/<span
                                                        name="pathProject">Projekt</span>/</p>
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
                                        <tbody id="groupTable">

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
                                            <p id="student"><?php echo $userName; ?>/<span
                                                        name="pathProject">Projekt</span>/<span
                                                        name="pathGruppe">Gruppe</span>/</p>
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
                                        <tbody id="studentTable">

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