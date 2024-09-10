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
            <form action="user.jsp" method="post">
                <p>Имя: <input type="text" name="username"></p>
                <p>Пол: <br>
                    <label for="female"><input type="radio" name="gender" value="Ж" id="female"> Ж</label><br>
                    <label for="male"><input type="radio" name="gender" value="М" id="male"> М</label><br>
                </p>
                <p>Страна:
                    <select name="country">
                        <option>Россия</option>
                        <option>Беларусь</option>
                        <option>Турция</option>
                    </select>
                </p>
                <p>Курса обучения: <br>
                    <label for="java"><input type="checkbox" name="courses" value="Java" id="java"> Java</label><br>
                    <label for="HTML"><input type="checkbox" name="courses" value="HTML" id="HTML"> HTML</label><br>
                    <label for="js"><input type="checkbox" name="courses" value="JavaScript" id="js"> JS</label><br>
                </p>
                <p><input type="submit" value="Подтвердить"></p>
            </form>
        </section>
        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>
