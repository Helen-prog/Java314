package org.ee.jakarta.bookwebapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final String query = "SELECT * FROM book_data";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        out.println("<html><head><link rel='stylesheet' href='css/style.css'></head><body><div class='list'>");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_app", "root", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Идентификатор книги</th>");
            out.println("<th>Название книги</th>");
            out.println("<th>Книжное издание</th>");
            out.println("<th>Цена книги</th>");
            out.println("<th>Редактировать</th>");
            out.println("<th>Удалить</th>");
            out.println("</tr>");
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt(1) + "</td>");
                out.println("<td>" + resultSet.getString(2) + "</td>");
                out.println("<td>" + resultSet.getString(3) + "</td>");
                out.println("<td>" + resultSet.getFloat(4) + "</td>");
                out.println("<td><a href='editScreen?id=" + resultSet.getInt(1) + "'>Редактировать</a></td>");
                out.println("<td><a href='deleteUrl?id=" + resultSet.getInt(1) + "'>Удалить</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h2>" + e.getMessage() + "</h2>");
        }
        out.println("<a href='home.html'>Главная</a>");
        out.println("</div></body></html>");
    }
}
