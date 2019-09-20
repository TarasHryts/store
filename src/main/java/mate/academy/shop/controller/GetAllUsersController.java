package mate.academy.shop.controller;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GetAllUsersController extends HttpServlet {
    final static Logger logger = Logger.getLogger(FileReader.class);
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + "start working");
        List<User> users = userService.getAll();
        req.setAttribute("greeting", "Mates");
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/allUsers.jsp").forward(req, resp);
    }
}
