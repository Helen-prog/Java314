package org.ee.jakarta.bookwebapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT book_title, book_edition, book_price FROM book_data WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        out.println("<html><head><link rel='stylesheet' href='css/style.css'></head><body><div class='list'>");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_app", "root", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            out.println("<form action='editUrl?id="+ id +"' method='post'>");
            out.println("<table>");

            out.println("<tr>");
            out.println("<td>Название книги</td>");
            out.println("<td><input type='text' name='bookTitle' value='"+ resultSet.getString(1) +"'></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Книжное издание</td>");
            out.println("<td><input type='text' name='bookEdition' value='"+ resultSet.getString(2) +"'></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Цена</td>");
            out.println("<td><input type='text' name='bookPrice' value='"+ resultSet.getFloat(3) +"'></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td><input type='submit' value='изменить' class='submit'></td>");
            out.println("<td><input type='reset' value='отменить' class='reset'></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h2>" + e.getMessage() + "</h2>");
        }
        out.println("<a href='home.html'>Главная</a>");
        out.println("</div></body></html>");
    }
}
