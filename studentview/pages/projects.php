<?php
include_once '../database/tokenSetter.php';
?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>dozent-view-alternativ slider</title>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/fonts/ionicons.min.css">
    <link rel="stylesheet" href="../assets/css/Contact-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/Customizable-Carousel-swipe-enabled.css">
    <link rel="stylesheet" href="../assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/MUSA_panel-table.css">
    <link rel="stylesheet" href="../assets/css/MUSA_panel-table1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu1.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet'
          type='text/css'>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/projects.css">


    <script src="../assets/js/jquery.min.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Customizable-Carousel-swipe-enabled.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>
    <script src="../assets/js/getProjects.js"></script>
    <script src="../assets/js/overview.js"></script>
    <script src="../assets/js/showProjects.js"></script>
</head>

<body>

<p id="user" hidden><?php echo $userName; ?></p>


<!-- the delete dialog -->

<!-- Modal -->
<div id="deleteModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Modal Header</h4>
            </div>
            <div class="modal-body">
                <p>Some text in the modal.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>

<!-- the slideshow -->
<div id="wrapper" style="margin:0px;">
    <?php
    include_once 'menu.php'
    ?>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div>

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
                                            <h3 class="panel-title" style="margin-top:50px;">Projekte</h3>
                                            <p><?php echo $userName; ?>/</p>
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
                                        <div class="col col-xs-4" style="display: none">Projekte
                                        </div>
                                        <div class="col col-xs-6 text-right">
                                            <button type="button" class="btn btn-sm btn-primary btn-create"
                                                    onclick="location.href='newproject.php?token=<?php echo $token ?>'">
                                                neues Projekt erstellen
                                            </button>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>
                        <p></p>

                   <!--     <div class="alert alert-primary projectAlert" role="alert">
                            Hier können Sie ihre Projekte, deren Gruppen und Studenten einsehen. Dafür klicken sie auf
                                das Projekt, das sie sehen wollen.
                        </div>-->
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="container">
                    <div class="row">

                        <div class="col-md-10 col-md-offset-1">

                            <div class="panel panel-default panel-table" style="width:550px">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col col-xs-6">
                                            <h3 class="panel-title" style="margin-top:50px;">Gruppen</h3>
                                            <p id="gruppe"><?php echo $userName; ?>/<span
                                                    name="pathProject">Projekt</span>/</p>
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
                        <p></p>
                        <!--<p> Hier können Sie die Gruppen und deren Studenten einsehen. Klicken sie auf
                            die Gruppe, die sie sehen detaillierter wollen.<br>
                            Zurück kommen sie mit dem Button links unten.</p>-->
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="container">
                    <div class="row">

                        <div class="col-md-10 col-md-offset-1">

                            <div class="panel panel-default panel-table" style="width:550px">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div class="col col-xs-6">
                                            <h3 class="panel-title" style="margin-top:50px;">Studenten</h3>
                                            <p id="student"><?php echo $userName; ?>/<span
                                                    name="pathProject">Projekt</span>/<span
                                                    name="pathGruppe">Gruppe</span>/</p>
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
                        <p></p>
                        </br>
                        <!--    <div class="alert alert-primary" role="alert">
                                Hier können Sie die Daten der Studenten einsehen.
                                Zurück kommen sie mit dem Button links unten.
                            </div>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>