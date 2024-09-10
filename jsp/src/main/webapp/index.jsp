<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>

<h3>Изучаем JSP</h3>

<p><%= LocalDate.now() %></p>

<%!
    int a = 10;
    String name = "jsp";

    int square(int n) {
        return n * n;
    }
%>

<%
    out.println("a: " + a + "<br>");
    int b = 20;
    if (b < 100) {
        out.println(b + " меньше 100<br>");
    } else {
        out.println(b + " больше 100<br>");
    }
    for (int i=1; i<=10; i++) {
        out.println(square(i) + "<br>");
    }
%>
<%= square(6) %>
<%= "a: " + a %>

<%
    String title = "Hello World!!!";
    int len = title.length();
    String[] people = new String[]{"Tom", "Bob", "Sam"};
%>

<ul>
    <%
        for (String p : people) {
            out.println("<li>" + p + "</li>");
        }
    %>
</ul>

<p>Длина строки: <%= title %> = <%= len %> символов</p>
<p>Сегодня: <%= new java.util.Date() %>
<p>Сегодня: <%= new Date() %>
</p>

<p>2 + 2 = <%= 2 + 2 %>
</p>
<p>5 > 2 = <%= 5 > 2 %>
</p>
<p><%= new String("Hello").toUpperCase() %>
</p>

<%
    for (int i = 0; i < 5; i++) {
        out.println("<br>Hello " + i);
    }
%>


</body>
</html>