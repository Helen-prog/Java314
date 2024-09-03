package org.ee.jakarta;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = userService.getUser(1L);
        resp.setContentType("text/html");
        var writer = resp.getWriter();
        writer.println("<html><body>");
        writer.println("<h1>Пользователь: </h1>");
        writer.println("<h2>"+user.get().getName()+"</h2>");
        writer.println("</body></html>");
        writer.close();
    }
}
