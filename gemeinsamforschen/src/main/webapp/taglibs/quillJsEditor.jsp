<%--
  Created by IntelliJ IDEA.
  User: martinstahr
  Date: 2019-04-03
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String readOnlyString = request.getParameter("readOnly");
    boolean readOnlyBoolean = Boolean.valueOf(readOnlyString);
%>
<script>
    const quill = new Quill('#editor', {
        theme: 'snow',
        readOnly: <%=readOnlyBoolean%>,
        "modules": {
            "toolbar": <%=!readOnlyBoolean%>
        }
    });
</script>