<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String firstName = request.getParameter("name1");
    out.println(firstName);
%>
<%
    String cname = (String) session.getAttribute("session_name");
    out.println(cname);
%>
</body>
</html>
