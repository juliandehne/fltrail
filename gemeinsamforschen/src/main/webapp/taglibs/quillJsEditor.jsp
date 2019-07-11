<%@ page import="com.google.common.base.Strings" %><%--
  Created by IntelliJ IDEA.
  User: martinstahr
  Date: 2019-04-03
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String readOnlyString = request.getParameter("readOnly");
    boolean readOnlyBoolean = !Strings.isNullOrEmpty(readOnlyString) ? Boolean.valueOf(readOnlyString) : false;
%>
<script>
    const quill = new Quill('#editor', {
        theme: 'snow',
        readOnly: <%=readOnlyBoolean%>,
        "modules": {
            <%if (!readOnlyBoolean){%>
            "toolbar": [
                [{header: [1, 2, false]}],
                ['bold', 'italic', 'underline'],
                ['image', 'code-block']
            ]
            <%}else{%>
            "toolbar": false
            <%}%>
        }
    });
</script>