package mate.academy.shop.controller;

import static mate.academy.shop.model.Role.RoleName.ADMIN;
import static mate.academy.shop.model.Role.RoleName.USER;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.factory.Util;
import mate.academy.shop.model.Role;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Inject
    private static UserService userService;
    @Inject
    private static RoleDao roleDao;

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
        String password = Util.hashPassword(req.getParameter("psw"),
                userService.getByLogin(login).get().getSalt());
        try {
            User user = userService.login(login, password).get();
            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            if (verifyRole(user, USER)) {
                resp.sendRedirect(req.getContextPath() + "/indexUserPanel");
            }
            if (verifyRole(user, ADMIN)) {
                resp.sendRedirect(req.getContextPath() + "/indexAdminPanel");
            }
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return roleDao.getAllRoleForUser(user.getId())
                .stream().anyMatch(x -> x.getRoleName().equals(roleName));
    }
}
