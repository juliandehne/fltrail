<?php
$menuWidth=strlen($userName).'%';
if (strlen($userName)<11){
    $menuWidth = '170px';
}
?>
<style>
    @media screen and (min-width:700px){
        #sidebar-wrapper {
            width: <?php echo $menuWidth;?>;
            overflow-x: hidden;
        }
    }
    @media screen and (max-width:699px){
        #sidebar-wrapper {
            width: 170px;
            overflow-x: hidden;
        }
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