<?php
$menuWidth=strlen($userName);
if ($menuWidth<11){
    $menuWidth = 11;
}
?>
<style>
#sidebar-wrapper {
    width: <?php echo $menuWidth;?>%;
    overflow-x: hidden;
}
ul{
    width: 100%;
}
li{
    width:95%;
}
a{
    margin-top:11%;
    width:100%;
}
h4{
    margin-top:15%;
    color:white;
}
</style>