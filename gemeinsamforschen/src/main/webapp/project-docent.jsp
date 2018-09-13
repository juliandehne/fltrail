<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="communication" uri="/communication/chatWindow.tld" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>



<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="0"/>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="0"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <button
                class="btn btn-default" type="button">Exportiere Zwischenstand
        </button>
        <button class="btn btn-default" type="button">Quizfrage erstellen</button>
        <div>
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Gruppe1</th>
                                    <th>Beiträge</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>student1</td>
                                    <td>Interfaces</td>
                                </tr>
                                <tr>
                                    <td>student2</td>
                                    <td>Design</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Gruppe2</th>
                                    <th>Beiträge</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>student3</td>
                                    <td>Interfaces</td>
                                </tr>
                                <tr>
                                    <td>student4</td>
                                    <td>Design</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Gruppe3</th>
                                    <th>Beiträge</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>student5</td>
                                    <td>Interfaces</td>
                                </tr>
                                <tr>
                                    <td>student6</td>
                                    <td>Design</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer:footer/>
</div>
<communication:chatWindow orientation="right" />
</body>

</html>