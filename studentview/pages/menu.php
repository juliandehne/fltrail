<?php
/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 16.03.2018
 * Time: 10:05
 */
?>
<div id="sidebar-wrapper" style="width:190px;">
        <ul class="sidebar-nav" style="width:200px;margin-top:50px">
            <li><h4 style="color:white;display:none;"><?php echo $userName; ?></h4></li>
<li style="width:146px;"><a href="newproject.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekt erstellen</a></li>

<li style="width:146px;"><a href="enrollment.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekt beitreten</a>
</li>
<li style="width:146px;"><a href="projects.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekte anzeigen</a></li>
<li style="width:146px;"><a href="mygroups.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;"> Gruppen anzeigen</a></li>
<li style="width:146px;"><a href="../index.php" style="margin-top:134px;width:200px;">Logout </a>
</li>
</ul>
</div>