package mate.academy.shop.controller;

import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.User;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class RegistrationController extends HttpServlet {
    final static Logger logger = Logger.getLogger(FileReader.class);
    @Inject
    private static UserService userService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + "start working");
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        User newUser = new User();
        Bucket newBucket = new Bucket(newUser.getId());
        bucketService.create(newBucket);
        newUser.setLogin(req.getParameter("login"));
        newUser.setPassword(req.getParameter("psw"));
        newUser.setName(req.getParameter("user_name"));
        newUser.setSurname(req.getParameter("user_surname"));
        newUser.setBucket(newBucket);
        User user = userService.create(newUser);
        HttpSession session = req.getSession(true);
        session.setAttribute("userId", user.getId());
        Cookie cookie = new Cookie("MATE", user.getToken());
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
