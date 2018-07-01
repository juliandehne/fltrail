<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Tagebucheintrag erstellen</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="../assets/css/styles.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
	<link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
	<link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
	<link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
	<link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
	<link rel="stylesheet" type="text/css" href="../assets/css/create-journal.css">

</head>

<body>
<div id="wrapper">
	<menu:menu></menu:menu>

	<div class="page-content-wrapper">
		<div class="container-fluid">
			<h1 id="projectId">project1
				<a href="#">
                <span class="glyphicon glyphicon-envelope"
					  style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
				</a>
				<a href="#">
					<span class="glyphicon glyphicon-cog" style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
				</a></h1>
		</div>
		<div>
			<table>
				<tr>
					<td  id="yourContent">
						<h1> Tagebucheintrag erstellen </h1>

						<form id="journalform" class="form-journal" method="POST" action="../rest/journal/save" >

							<input type="hidden" name="student" value="0">
							<input type="hidden" name="project" value="0">

							<div class="journal-form-container">

								<div class = "journal-form-visibility">
									Sichtbarkeit:
									<select id="visibility" name="visibility" form="journalform">
										<option value="ALL"> Alle </option>
										<option value="GROUP"> Gruppe </option>
										<option value="DOZENT"> Dozent </option>
										<option value="NONE"> Nur Ich </option>
									</select>
								</div>

								<div class = "journal-form-category">
									Kategorie:
									<select name="category" form="journalform">
										<option value="TITEL"> Titel </option>
										<option value="RECHERCHE"> Recherche </option>
										<option value="LITERATURVERZEICHNIS"> Literaturverzeichnis </option>
										<option value="FORSCHUNGSFRAGE"> Forschungsfrage </option>
										<option value="UNTERSUCHUNGSKONZEPT"> Untersuchungskonzept </option>
										<option value="METHODIK"> Methodik </option>
										<option value="DURCHFUEHRUNG"> Durchführung </option>
										<option value="AUSWERTUNG"> Auswertung </option>

									</select>
								</div>


								<div class ="journal-form-editor">
                                    <textarea id = "editor" name="text" form="journalform" >
                                    </textarea>
								</div>

								<div class="journal-form-buttons">
									<input class="btn btn-default btn-sm" type="submit">
									<a class="btn btn-default btn-sm" href="eportfolio.jsp"> Zur&uuml;ck </a>
								</div>

							</div>
						</form>

					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
<script  src="../assets/js/createJournal.js"></script>
</body>

</html>