<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Portfolio</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/e-portfolio.css">
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

                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#description">Beschreibung</a></li>
                            <li><a data-toggle="tab" href="#journal-container">Lerntagebuch</a></li>
                         </ul>
                        <div class="tab-content">

                            <div id = "description" class="tab-pane fade in active ">
                              <div class="journal-description-container">
                                    <div class="journal-description-title">
                                    </div>
                                    <div class="journal-description-edit" align="right">
                                        <a class="btn btn-default btn-sm" href="editDescription.jsp?project=0&token=test">
                                            <i class="fa fa-pencil"></i> Bearbeiten</a>
                                        <a class="btn btn-default btn-sm" href="#">
                                            <i class="fa fa-check-square" aria-hidden="true"></i>Abschlie&szlig;en</a>

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
                            </div>

                            <div id="journal-container" class="tab-pane fade">
                                <h2>Lernatagebuch</h2>

                                <select id="journalfilter" onchange="filterJournals()">
                                    <option value="ALL">Alle</option>
                                    <option value="OWN">Eigene</option>
                                </select>

                                <a class="btn btn-default btn-sm" href="createJournal.jsp?token=test">Neu</a>

                                <div class="journal">
                                </div>
                        </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="addLinkModal" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Link hinzuf&uuml;gen</h4>
            </div>
            <div class="modal-body">
                <form id="linkform" method="POST" action="../rest/projectdescription/addLink" >
                    Name:<br>
                    <input type="text" name="name" form="linkform">
                    <br>
                    URL:<br>
                    <input type="text" name="link" form="linkform">
                    <br><br>
                    <input class="btn btn-default" type="submit"  >
                    <button type="button" class="btn btn-default" data-dismiss="modal">Abbrechen</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
<script src="../assets/js/e-portfolio.js"></script>
</body>

</html>