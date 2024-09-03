package org.ee.jakarta.servletapplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("INIT");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        var writer = resp.getWriter();
        writer.println("<h1>Привет Сервлет</h1>");
    }

    @Override
    public void destroy() {
        System.out.println("DESTROY");
    }
}
