<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/courses-student.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>
<div class="col span_content">
    <div>
            <h1 id="headLine"></h1>
            <p class="introduction"> Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod
                tempor
                invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo
                dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit
                amet.</p>

            <!-- filter-->
            <div class="filter" id="projectDropdown">
                <script id="searchingTemplate" type="text/x-jQuery-tmpl">
            <div class="projectDynamic">
                <select>
                    {{each(prop,val) projects}}
                        <option value="${val}">${val}</option>
                    {{/each}}
                </select>


                </script>
                <div class="select_arrow"></div>
            </div>
        <div class="search">
            <input id="searchField" type="text" name="suche" placeholder="Suche"><i class="fas fa-search"></i>
        </div>
    </div>

    <div class="row group projects-grid" id="projects">
        <script id="projectTemplate" type="text/x-jQuery-tmpl">
            <div class="card projectDynamic">
                <div>
                    <h3>${projectName}</h3>
                    <p>
                        ${projectDescription}
                    </p>
                    <label>Tags:
                        <div class='tags'>
                        {{each(i) projectTags}}
                            <span class='tag'>${projectTags[i]}</span><div class="spacing"></div>
                        {{/each}}
                        </div>
                    </label>
                    <button class="primary project_Button" name="${projectName}" id="project_${projectName}">${projectAction} </button>
                </div>
            </div>
        </script>
    </div>
</div>
<footer:footer/>
</body>

</html>