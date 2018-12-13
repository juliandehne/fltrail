<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>
    <script src="js/assess-work.js"></script>

</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">     <jsp:param name="hierarchy" value="1"/> </jsp:include> <main> <jsp:include page="../taglibs/timeLine.jsp" /><div class="col span_content">
<div id="wrapper">
    <div class="page-content-wrapper">
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>Assessment</h1>

                        <!-- Vorschläge für Bewertungen:
                            ++Verantwortungsbewusstsein
                            ++Disskusionsfähigkeit
                            ++Anteil am Produkt
                            ++Kooperationsbereitschaft
                            ++Selbstständigkeit
                            -+Führungsqualität
                            -+Pünktlichkeit
                            -+Motivation
                            -+Gewissenhaftigkeit
                            -+respektvoller Umgang mit anderen
                            -+Wert der Beiträge
                            --kann sich an Vereinbarungen halten
                            --emotionale Stabilität
                            --Hilfsbereitschaft
                        -->

                        <!-- here will be all the content -->
                        <div class="container">
                            <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
                                <!-- Wrapper for slides -->
                                <div class="alert alert-info" id="notAllRated">
                                    Es wurden noch nicht alle Studenten vollständig bewertet
                                </div>

                                <div class="carousel-inner" id="peerTable">
                                    <script id="peerTemplate" type="text/x-jQuery-tmpl">
                                        {{if first}}
                                        <div class="item active">
                                        {{else}}
                                        <div class="item">
                                        {{/if}}
                                        <table class="table-striped peerStudent" id="${peerId}">
                                        <tr>
                                        <td align="center">
                                        <img src="../libs/img/noImg.png" alt="${peerId}" style="width:20%;">
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <h3>Verantwortungsbewusstsein</h3>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <label>stark ausgeprägt<input type="radio" value="5" name="responsibility${peerId}"></label>
                                        <input type="radio" value="4" name="responsibility${peerId}">
                                        <input type="radio" value="3" name="responsibility${peerId}">
                                        <input type="radio" value="2" name="responsibility${peerId}">
                                        <label><input type="radio" value="1" name="responsibility${peerId}">ungenügend</label>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <h3>Anteil am Produkt</h3>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <label>großer Anteil<input type="radio" value="5" name="partOfWork${peerId}"></label>
                                        <input type="radio" value="4" name="partOfWork${peerId}">
                                        <input type="radio" value="3" name="partOfWork${peerId}">
                                        <input type="radio" value="2" name="partOfWork${peerId}">
                                        <label><input type="radio" value="1" name="partOfWork${peerId}">geringer Anteil</label>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <h3>Kooperationsbereitschaft</h3>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <label>sehr kooperativ<input type="radio" value="5" name="cooperation${peerId}">
                                        </label>
                                        <input type="radio" value="4" name="cooperation${peerId}">
                                        <input type="radio" value="3" name="cooperation${peerId}">
                                        <input type="radio" value="2" name="cooperation${peerId}">
                                        <label><input type="radio" value="1" name="cooperation${peerId}">nicht kooperativ</label>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <h3>Disskusionsfähigkeit</h3>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <label>gut kommuniziert und Meinung vertreten<input type="radio" value="5" name="communication${peerId}">
                                        </label>
                                        <input type="radio" value="4" name="communication${peerId}">
                                        <input type="radio" value="3" name="communication${peerId}">
                                        <input type="radio" value="2" name="communication${peerId}">
                                        <label><input type="radio" value="1" name="communication${peerId}">keine Meinung und schlecht kommuniziert</label>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <h3>Selbstständigkeit</h3>
                                        </td>
                                        </tr>
                                        <tr>
                                        <td align="center">
                                        <label>selbstständig<input type="radio" value="5" name="autonomous${peerId}">
                                        </label>
                                        <input type="radio" value="4" name="autonomous${peerId}">
                                        <input type="radio" value="3" name="autonomous${peerId}">
                                        <input type="radio" value="2" name="autonomous${peerId}">
                                        <label><input type="radio" value="1" name="autonomous${peerId}">abhängig</label>
                                        </td>
                                        </tr>
                                        </table>
                                        <div align="center">
                                        <button class="btn btn-primary" id="btnJournal${peerId}">
                                        zeige Lernzieltagebuch</button>
                                        <div id="eJournal${peerId}">Fasel Blubba Bla</div>
                                        </div>
                                        </div>
                                    </script>
                                </div>

                                <!-- Left and right controls -->
                                <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#myCarousel" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                            <button class="btn btn-success" id="assessThePeer">Gruppe bewerten</button>
                        </div>
                    </td>
                    <td id="chat">
                        <div class="card">
                            <div class="card-header">
                                <h6 class="mb-0">Gruppen+Projekt Chat</h6>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </div><div class="col span_chat">
    <chat:chatWindow orientation="right" scope="project" />
    <chat:chatWindow orientation="right" scope="group" />
</div>
<jsp:include page="../taglibs/footer.jsp"/>
</div>

</body>

</html>