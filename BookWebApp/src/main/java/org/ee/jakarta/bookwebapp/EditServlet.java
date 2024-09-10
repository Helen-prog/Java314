package org.ee.jakarta.bookwebapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/editUrl")
public class EditServlet extends HttpServlet {
    private static final String query = "UPDATE book_data SET book_title=?, book_edition=?, book_price=? WHERE id=?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        String bookTitle = req.getParameter("bookTitle");
        String bookEdition = req.getParameter("bookEdition");
        String bookPrice = req.getParameter("bookPrice");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        out.println("<html><head><link rel='stylesheet' href='css/style.css'></head><body><div class='list'>");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:///book_app", "root", "123456");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookTitle);
            preparedStatement.setString(2, bookEdition);
            preparedStatement.setString(3, bookPrice);
            preparedStatement.setInt(4, id);
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                out.println("<h2>Редактирование прошло успешно</h2>");
            } else {
                out.println("<h2>Редактирование не удалось</h2>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h2>" + e.getMessage() + "</h2>");
        }
        out.println("<a href='home.html'>Главная</a>");
        out.println("</br><a href='bookList'>Список книг</a>");
        out.println("</div></body></html>");
    }
}
