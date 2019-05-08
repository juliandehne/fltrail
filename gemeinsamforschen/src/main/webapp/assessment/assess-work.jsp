<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <script src="js/assess-work.js"></script>

</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main>
        <jsp:include page="../taglibs/timeLine.jsp"/>
        <div class="col span_content span_l_of_2">
            <div>
                <h1>Assessment</h1>
                <!-- here will be all the content -->
                <div id="myCarousel" class="carousel slide" data-ride="carousel"
                     data-interval="false">
                    <!-- Wrapper for slides -->
                    <div class="alert alert-info" id="notAllRated">
                        Es wurden noch nicht alle Studenten vollständig bewertet
                    </div>

                    <div id="surveyContainer"></div>
                    <!--<div class="carousel-inner" id="peerTable">
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
                                    {{each(prop, val) properties}}
                                        <tr>
                                            <td align="center">
                                                <h3>${val}</h3>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <label>stark ausgeprägt<input type="radio" value="5" name="${val}${peerId}"></label>
                                                <input type="radio" value="4" name="${val}${peerId}">
                                                <input type="radio" value="3" name="${val}${peerId}">
                                                <input type="radio" value="2" name="${val}${peerId}">
                                                <label><input type="radio" value="1" name="${val}${peerId}">ungenügend</label>
                                            </td>
                                        </tr>
                                    {{/each}}
                                </table>
                             <div align="center">
                             <button class="btn btn-primary" id="btnJournal${peerId}">zeige Lernzieltagebuch</button>
                             <div id="eJournal${peerId}">Fasel Blubba Bla</div>
                             </div>
                             </div>
                        </script>
                    </div>

                    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                        <span class="sr-only">Next</span>
                    </a>-->  <!--old solution... just nostalgia keeps me from deleting -->
                </div>
                <button class="btn btn-success" id="assessThePeer">Gruppe bewerten</button>
            </div>
            <div class="col span_chat">
                <chat:chatWindow orientation="right" scope="project"/>
                <chat:chatWindow orientation="right" scope="group"/>
            </div>
            <jsp:include page="../taglibs/footer.jsp"/>
        </div>
    </main>
    </div>
</body>

</html>