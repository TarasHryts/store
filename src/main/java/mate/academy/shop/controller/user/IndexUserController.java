package mate.academy.shop.controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class IndexUserController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(IndexUserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        req.getRequestDispatcher("/WEB-INF/views/indexUserPanel.jsp").forward(req, resp);
    }
}
