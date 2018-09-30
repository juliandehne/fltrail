<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--suppress XmlDuplicatedId --%>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>


<!--todo: E-mail an Studenten als Notifikation für Phasenwechsel -->


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="0"/>
    <script src="core/project-student.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="0"/>

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
                                    <h3 id="name-student">teststudent5</h3>
                                    <img src="libs/img/1.jpg">
                                    <a href="#">test5@uni.de</a>
                                    <hr>
                                    <ul>

                                        <li>
                                            Projektübersicht
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
                                    <h3>teststudent2</h3>
                                    <img src="libs/img/2.jpg">
                                    <a href="#">test2@uni.de</a>
                                    <hr>
                                    <ul>
                                        <li>
                                            Blumen an Vegetarier verfüttert
                                            <%--<a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>--%>
                                        </li>
                                        <li>
                                            "Forschungsfrage: Warum essen Vegetarier Blumen?"
                                            <%--<a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>--%>
                                        </li>
                                        <li>
                                            Die armen Vegetarier
                                            <%--<a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>--%>
                                        </li>
                                    </ul>
                                </td>
                                <td></td>

                                <td width="100px" valign="top">
                                    <h3>teststudent3</h3>
                                    <img src="libs/img/3.jpg">
                                    <a href="#">test3@uni.de</a>
                                    <hr>
                                    <ul>
                                        <li>
                                            Vielen Dank für die Blumen
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Durchführung
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                    </ul>
                                </td>

                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <footer:footer/>

</div>

</body>

</html>