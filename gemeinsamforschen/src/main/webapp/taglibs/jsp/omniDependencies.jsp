<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %><%--
  Created by IntelliJ IDEA.
  User: fides-WHK
  Date: 07.12.2018
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<% String hierarchyLevel = request.getParameter("hierarchy");%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/img/favicon.ico"
      type="image/x-icon"/>
<link rel="icon" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/img/favicon.ico"
      type="image/x-icon"/>
<link href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/css/googleAPIS400-700.css" rel="stylesheet">
<link href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/css/googleAPIS300-400-700.css" rel="stylesheet">

<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/bootstrap/css/bootstrap3.3.7.min.css">
<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/css/normalize.css">

<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/css/global.css">

<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/css/styles.css">
<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/css/footer.css">

<!--<link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/fonts/fa5-all.css\">-->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css">
<!--integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">-->
<script src="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/jquery/jquery.3.3.1.min.js"></script>
<script src="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/jquery/jquery.3.3.7.min.js"></script>
<!--<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/fonts/font-awesome.min.css\">-->
<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/css/Sidebar-Menu-1.css">
<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/css/Sidebar-Menu.css">
<script src="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/js/utility.js"></script>
<script src="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>taglibs/js/footer.js"></script>
<!--<link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "project/css/all.css\">-->
<!--<link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "project/css/style.css\" type=\"text/css\">-->
<link rel="stylesheet" href="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/tagsinput/jquery.tagsinput.min.css">
<script src="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/tagsinput/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="<%=new TagUtilities().hierarchyToString(hierarchyLevel)%>libs/jquery/jqueryTemplate.js"></script>

