package mate.academy.shop.controller.adminControllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class IndexAdminController extends HttpServlet {
    private final static Logger logger = Logger.getLogger(IndexAdminController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        req.getRequestDispatcher("/WEB-INF/views/indexAdminPanel.jsp").forward(req, resp);
    }
}
