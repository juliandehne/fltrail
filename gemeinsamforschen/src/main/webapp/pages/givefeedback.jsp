
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>muster-gemeinsam-forschen</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://cdn.rawgit.com/showdownjs/showdown/1.8.5/dist/showdown.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/viewfeedback.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId"> PeerFeedback</h1>
        </div>
        <div align="right" class="dropdown" >
            <button style= "position: absolute; right: 50px;" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">

                <i class="glyphicon glyphicon-envelope"></i>
            </button>

            <ul class="dropdown-menu">
                <li><a id="viewfeedback" role="button">Feedback A</a></li>
                <li><a id="viewfeedback" role="button">Feedback B</a></li>
                <li><a id="viewfeedback" role="button">Feedback C</a></li>
            </ul>

            <a href="#">
                <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-right:30px;margin-top:3px;"></span>
            </a>

        </div>

        <div>

        </div>
        <div>
            <table>
                <tr>
                    <h2> Gib dein Feedback ein!</h2>
                    <div class="line-spacer"></div>
                    <p><span> Datei zum Feedback: SelectedFile.pdf </span></p>
                    <p class="text-primary"><span> Kategorie: Untersuchungskonzept </span></p>
                    <hr />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                        <label class="form-check-label" for="defaultCheck1">
                            Das fand ich gut
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
                        <label class="form-check-label" for="defaultCheck1">
                            Ich habe noch eine Frage
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck3">
                        <label class="form-check-label" for="defaultCheck1">
                            Das wuerde ich anders machen
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="defaultCheck4">
                        <label class="form-check-label" for="defaultCheck1">
                            Ich habe eine Idee
                        </label>
                    </div>
                    <hr />
                    <div class="line-spacer"></div>
                    <td  id="Peerfeedback" valign="top">


                        <div style="height:100px;padding-bottom:100px;">
			            <textarea id="demo1">
				            Das ist ein Test!
			            </textarea>
                        </div>

                        <button onclick="save()">Speichern</button>

                        <p id = "output"> Output... </p>
                        <script>
                            var editor = new InscrybMDE({
                                element: document.getElementById("demo1"),
                                spellChecker: false,
                                //toolbar: ["bold", "italic", "heading", "|", "quote", "table", "code", "|" , "side-by-side", "fullscreen"],
                            });


                            function save() {
                                console.log("save");
                                console.log(editor.value());

                                var converter = new showdown.Converter(),
                                    text      = editor.value(),
                                    html      = converter.makeHtml(text);

                                document.getElementById('output').innerHTML = html;11
                            }

                        </script>
                        <div>
                            <button class="btn btn-light">Next</button>
                            <button class="btn btn-light" id="">Back</button>
                        </div>
                    </td>

            <td  id="chat">
             <div class="card">
                 <div class="card-header">
                     <h6 class="mb-0">Gruppen+Projekt Chat</h6>
                 </div>
                 <div class="card-body">
                     <ul class="list-group">
                         <li class="list-group-item">
                             <div class="media">
                                 <div></div>
                                 <div class="media-body">
                                     <div class="media" style="overflow:visible;">
                                         <div><img src="../assets/img/1.jpg" class="mr-3"
                                                   style="width: 25px; height:25px;"></div>
                                         <div class="media-body" style="overflow:visible;">
                                             <div class="row">
                                                 <div class="col-md-12">
                                                     <p><a href="#">Sara Doe:</a> This guy has been going
                                                         100+ MPH on side streets. <br>
                                                         <small class="text-muted">August 6, 2016 @ 10:35am
                                                         </small>
                                                     </p>
                                                 </div>
                                             </div>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                         </li>
                         <li class="list-group-item">
                             <div class="media">
                                 <div></div>
                                 <div class="media-body">
                                     <div class="media" style="overflow:visible;">
                                         <div><img src="../assets/img/2.jpg" class="mr-3"
                                                   style="width: 25px; height:25px;"></div>
                                         <div class="media-body" style="overflow:visible;">
                                             <div class="row">
                                                 <div class="col-md-12">
                                                     <p><a href="#">Brennan Prill:</a> This guy has been
                                                         going 100+ MPH on side streets. <br>
                                                         <small class="text-muted">August 6, 2016 @ 10:35am
                                                         </small>
                                                     </p>
                                                 </div>
                                             </div>
                                         </div>
                                     </div>
                                 </div>
                             </div>
                         </li>
                     </ul>
                     <button class="btn btn-light">
                         Add Comment
                     </button>
                 </div>
             </div>
            </td>
</tr>
</table>
</div>
</div>
</div>

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
</body>

</html>