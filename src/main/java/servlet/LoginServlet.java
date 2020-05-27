package servlet;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/index")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext()
                .getRequestDispatcher("/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

       PrintWriter printWriter = resp.getWriter();
        if (login.isEmpty() || password.isEmpty()) {
            printWriter.println("Data is empty!");
        }

        System.out.println("login: " + login + " password:" + password);
        User userTryLogin = UserService.getInstance().isExist(login, password);
        System.out.println("print user from session filter - userTryLogin : " + userTryLogin);
        String userRole = userTryLogin.getRole();
        System.out.println("print 1: " + userRole);
        HttpSession session = req.getSession();

        session.setAttribute("userTryLogin", userTryLogin);
        resp.sendRedirect("/admin");
    }
}