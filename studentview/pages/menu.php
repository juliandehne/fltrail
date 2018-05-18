<?php
/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 16.03.2018
 * Time: 10:05
 */

include "../assets/css/menu.php"
?>
<div id="sidebar-wrapper">
    <ul class="sidebar-nav">
        <li><h4><?php echo $userName; ?></h4></li>
        <li><a href="newproject.php?token=<?php echo $token ?>">Projekt erstellen</a></li>
        <li><a href="enrollment.php?token=<?php echo $token ?>">Projekt beitreten</a></li>
        <li><a href="projects.php?token=<?php echo $token ?>">Projekte anzeigen</a></li>
        <li><a href="mygroups.php?token=<?php echo $token ?>">Gruppen anzeigen</a></li>
        <li><a href="../index.php">Logout </a></li>
    </ul>
</div>