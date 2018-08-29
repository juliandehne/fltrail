<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="footer" %>
<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://cdn.rawgit.com/showdownjs/showdown/1.8.5/dist/showdown.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="../assets/js/rateContribution.js"></script>
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
                        <h2>letzter Schritt im Projekt1 </h2>
                        <table class="table-striped peerStudent"
                               style="width:100%;border:1px solid; margin:auto;" id="student1">
                            <tr>
                                <td align="center">
                                    <h3>Gruppe 4</h3>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="contributionRating" id="Dossier">
                                        Dossier:
                                        Die meisten Menschen sind bereit zu lernen, aber nur die wenigsten, sich
                                        belehren zu
                                        lassen.
                                        <textarea id="dossierFeedback">
				                        meine Bewertung
			                        </textarea>
                                        <label><input type="radio" name="dossier" value="5">Perfekt</label>
                                        <label><input type="radio" name="dossier" value="4">Makellos</label>
                                        <label><input type="radio" name="dossier" value="3">regulär</label>
                                        <label><input type="radio" name="dossier" value="2">Makelhaft</label>
                                        <label><input type="radio" name="dossier" value="1">Lädiert</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="contributionRating" id="research">
                                        Präsentation: <a href="#"><i class="fa fa-paperclip"></i></a>
                                        <textarea id="presentationFeedback">
				                        meine Bewertung
			                        </textarea>
                                        <label><input type="radio" name="research" value="5">Perfekt</label>
                                        <label><input type="radio" name="research" value="4">Makellos</label>
                                        <label><input type="radio" name="research" value="3">regulär</label>
                                        <label><input type="radio" name="research" value="2">Makelhaft</label>
                                        <label><input type="radio" name="research" value="1">Lädiert</label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <button id="submit" class="btn btn-success">Feedback hochladen</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<footer:footer/>
</body>

</html>