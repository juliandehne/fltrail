<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <link rel="stylesheet" href="css/all.css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="css/normalize.css" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Nunito:300,400,700" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <script src="js/search-project.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/><div class="col span_content">
<div>
    <div class="col span_2_of_2 centered">
        <h1>Projektsuche</h1>
        <p class="introduction"> Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>

        <!-- filter-->
        <div class="filter">
            <label>Ich suche nach: <select>
                <script id="searchingTemplate" type="text/x-jQuery-tmpl">
                    <option value="${searchOption}">${searchOption}</option>
                </script>
                <option value="saab">Saab</option>
                <option value="mercedes">Mercedes</option>
                <option value="audi">Audi</option>
            </select></label>
            <div class="select_arrow"></div>
        </div>
        <div class="search">
            <input  type="text" name="suche" placeholder="Suche"><i class="fas fa-search"></i>
        </div>
    </div>

    <div class="row group projects-grid" id="projects">
        <script id="projectTemplate" type="text/x-jQuery-tmpl">
        <div class="card">
            <div>
                <h3>${projectName}</h3>
                <p>
                    ${projectDescription}
                </p>
                <button class="primary" id="project_${projectName}">Beitreten </button>
            </div>
        </div>
        </script>
    </div>
</div>
</div><div class="col span_chat">
    <chat:chatWindow orientation="right" scope="project" />
    <chat:chatWindow orientation="right" scope="group" />
</div>
<footer:footer/>


</body>

</html>