package mate.academy.shop.controller;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.User;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

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
        logger.info(this.getClass().getName() + "start working");
        User newUser = new User();
        newUser.setLogin(req.getParameter("login"));
        newUser.setPassword(req.getParameter("psw"));
        newUser.setName(req.getParameter("user_name"));
        newUser.setSurname(req.getParameter("user_surname"));
        userService.create(newUser);
        Bucket newBucket = new Bucket(newUser.getId());
        bucketService.create(newBucket);

        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}

