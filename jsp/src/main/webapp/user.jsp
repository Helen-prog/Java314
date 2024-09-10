<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp" />
    <section>
        <p><b>Имя:</b> <%= request.getParameter("username") %></p>
        <p><b>Пол:</b> <%= request.getParameter("gender") %></p>
        <p><b>Страна:</b> <%= request.getParameter("country") %></p>
        <h4>Курс обучения</h4>
        <ul>
            <%
            String[] courses = request.getParameterValues("courses");
            for (String course : courses) {
                out.println("<li>" + course + "</li>");
            }
            %>
        </ul>
    </section>
    <jsp:include page="footer.jsp" />
</div>
</body>
</html>
