<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/courses-student.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<div class="row group">

</div>
<main class="projects">
    <div class="col span_content span_2_of_2 centered">
        <!-- filter-->
        <h2>Projekt finden</h2>
        <label class="container">Selbst erstellt
            <input type="checkbox" checked="checked">
            <span class="checkmark"></span>
        </label>

        <div class="filter">
            <!-- hier muessten die tags rein -->
            <select class="tags">
                <option value="volvo">Volvo</option>
                <option value="saab">Saab</option>
                <option value="mercedes">Mercedes</option>
                <option value="audi">Audi</option>

           </select>
            <div class="select_arrow"></div>
        </div>

        <div class="search">
            <input  type="text" name="suche" placeholder="Suche"><i class="fas fa-search"></i>
        </div>

    </div>

        <div class="row group projects-grid" id="projects">
            <script id="projectTemplate" type="text/x-jQuery-tmpl">
            <div class="card card-project projectDynamic">
                <div class="card-inner">
                    <h3>${projectName}</h3>
                    <p>
                        ${projectDescription}
                    </p>
                    <label>Tags:</label>
                        <div class='tags'>
                        {{each(i) projectTags}}
                            <span class='tag'>${projectTags[i]}</span><div class="spacing"></div>
                        {{/each}}
                        </div>

                    <button class="primary project_Button" name="${projectName}" id="project_${projectName}"
                    style="margin-top:10px;">${projectAction} </button>
                </div>
            </div>


    </script>


</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>