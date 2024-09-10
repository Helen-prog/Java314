<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        request.setAttribute("request_name", "Expression Language");
//        out.println(request.getAttribute("request_name"));
    %>

    ${requestScope}
    <p>Hello, ${requestScope.request_name}</p>

    <%
        session.setAttribute("session_cname", "Start Programming");
//        out.println(session.getAttribute("session_cname"));
    %>

    <h4>Course: ${session_cname}</h4>

    <form action="result.jsp" method="get">
        <input type="text" name="name1" placeholder="Введите имя">
        <input type="submit" value="Отправить">
    </form>

    ${ 10 + 5 }
</body>
</html>
