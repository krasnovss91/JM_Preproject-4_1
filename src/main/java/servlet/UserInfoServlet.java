package servlet;

import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("userTryLogin");
        PrintWriter printWriter = resp.getWriter();

        System.out.println("print user from USER servlet - userTryLogin : " + user);
        if (user == null) {
            printWriter.println("Access denied!");
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/jsp/user.jsp").forward(req, resp);

    }

}
