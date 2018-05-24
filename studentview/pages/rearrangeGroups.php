<?php
include_once '../database/tokenSetter.php';
include_once '../database/config.php';
//todo: right a hidden div with students and groups and refer to it on document ready in rearrange.js
?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>student-form-design</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="../assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu1.css">
    <link rel="stylesheet" href="../assets/css/styles.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    <script src="../assets/js/config.js"></script>
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/rearrange.js"></script>
    <script src="../assets/js/getProjects.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>


</head>

<body>
<p id="user"><?php echo $userName; ?></p>

<div id="wrapper">
    <?php
    include_once 'menu.php'
    ?>
    <div>
        <div class="container">
            <div class="row">
                <div class="col-md-offset-3 col-sm-8 col-xs-1">
                    <h3>Projekt <?php echo($_GET['projectId']); ?></h3>
                    <button class="btn btn-primary" type="button" id="automaticArrangement">automatisch zuordnen
                    </button>
                    <button class="btn btn-primary" type="button" id="saveArrangement">speichern</button>
                </div>
            </div>
            <div id="Groups">
            </div>

            <?php
            $projectId = $_GET['projectId'];
            $sql = "SELECT * FROM `groups` WHERE projectId = '" . $projectId . "' ORDER BY groupId ASC;";
            $result = mysqli_query($db, $sql);
            $oldGroup = '';
            $i = 0;
            $newStudent = mysqli_fetch_array($result);
            while ($newStudent) {
                $oldGroup = $newStudent[1];
                $students[] = $newStudent[2];
                $newStudent = mysqli_fetch_array($result);
                if ($oldGroup != $newStudent[1]) {
                    echo("
                <script> 
                    printGroupTable(" . json_encode($students) . ", ".$i.");
                </script>");
                    $i = $i+1;
                    $students=[];
                }
            }
            if ($oldGroup == '') {
                echo("<script> automaticArrangement('".$projectId."'); </script>");
            } else {
                echo ("
                <script>
                    printEmptyTable(".$i.");
                    for (var j = 0; j < ".$i." + 1; j++) {
                        $('#addToGruppe' + j).on('click', {group: 'Gruppe' + j, maxGroup: ".$i."}, reorderGroups);
                }
                </script>
                ");
            }
            mysqli_close($db);
            ?>

        </div>
    </div>
</div>
</body>

</html>