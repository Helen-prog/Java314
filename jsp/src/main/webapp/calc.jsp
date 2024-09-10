<%@ page import="application.Calculate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    for (int i=2; i<10; i++) {
        out.print("<p>" + i + "<sup>2</sup> = " + new Calculate().square(i) + "</p>");
    }
%>

</body>
</html>
