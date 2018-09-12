<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--suppress XmlDuplicatedId --%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="footer" %>



<!--todo: E-mail an Studenten als Notifikation für Phasenwechsel -->


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/project-student.js"></script>
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">

</head>

<body>
<div id="wrapper">
    <menu:menu/>

    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>Feedbackable Students</h1>
                        <!-- here will be all the content -->
                        <table id="myGroupMembers">
                            <tr>

                                <td width="100px" valign="top">
                                    <h3>student1</h3>
                                    <img src="../assets/img/1.jpg">
                                    <a href="#">student1@uni.de</a>
                                    <hr>
                                    <ul>

                                        <li>
                                            Projektübersicht hochgeladen
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Blumen ins Hausaufgabenheft geklebt
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                    </ul>
                                </td>
                                <td></td>

                                <td width="100px" valign="top">
                                    <h3>student2</h3>
                                    <img src="../assets/img/2.jpg">
                                    <a href="#">student2@uni.de</a>
                                    <hr>
                                    <ul>
                                        <li>
                                            Blumen an Vegetarier verfüttert
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Literaturverzeichnis hochgeladen
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Die armen Vegetarier
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                    </ul>
                                </td>
                                <td></td>

                                <td width="100px" valign="top">
                                    <h3>student3</h3>
                                    <img src="../assets/img/3.jpg">
                                    <a href="#">student3@uni.de</a>
                                    <hr>
                                    <ul id="submissionUpload">
                                    </ul>
                                </td>

                            </tr>
                        </table>

                        <button onclick="goBack()" class="btn btn-secondary">Zurueck</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <footer:footer/>

</div>

</body>

</html>