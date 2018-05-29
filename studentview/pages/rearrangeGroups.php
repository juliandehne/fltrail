<?php
include_once '../database/tokenSetter.php';
include_once '../database/config.php';
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
<?php
    $projectId = $_GET['projectId'];
    $sql = "SELECT * FROM `groups` WHERE projectId = '" . $projectId . "' ORDER BY groupId ASC;";
    $result = mysqli_query($db, $sql);
    $group = '';
    $i = 0;
    $student = mysqli_fetch_array($result);
    while ($student) {
        $group = $student[1];
        $students[] = $student[2];
        $student = mysqli_fetch_array($result);
        if ($group != $student[1]) {
            echo("<div id='students".$i."' hidden>".json_encode($students)."</div>");
            $i = $i+1;
            $students=[];
        }
    }
    mysqli_close($db);
?>
<p id="groups"></p>
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


        </div>
    </div>
</div>
</body>

</html>