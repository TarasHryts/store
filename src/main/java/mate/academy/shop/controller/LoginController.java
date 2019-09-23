package mate.academy.shop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LoginController.class);
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        String login = req.getParameter("login");
        String password = req.getParameter("psw");
        try {
            User user = userService.login(login, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + "/index");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
