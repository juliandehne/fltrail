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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    <script src="js/courses-student.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>
<div>
    <div class="col span_2_of_2 centered">
        <h1>
            <script type="application/javascript">
                // das ist dreckig und ich schäme mich dafür
                if (getQueryVariable("all")) {
                    document.write("Kurssuche");
                } else {
                    document.write("Meine Kurse")
                }
            </script>
        </h1>
        <p class="introduction"> Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
            invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo
            dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>

        <!-- filter-->
        <div class="filter" id="projectDropdown">
            <script id="searchingTemplate" type="text/x-jQuery-tmpl">
            <div id="projectDropdownDynamic">
                <select>
                    {{each(prop,val) projects}}
                        <option value="${val}">${val}</option>
                    {{/each}}
                </select>
            </script>
            <div class="select_arrow"></div>
        </div>
    </div>
    <div class="search">
        <input id="searchField" type="text" name="suche" placeholder="Suche"><i class="fas fa-search"></i>
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
                <button class="primary" id="project_${projectName}">Einsehen </button>
            </div>
        </div>

    </script>
</div>
</div>

<footer:footer/>


</body>

</html>