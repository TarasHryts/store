package mate.academy.shop.controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

public class IndexController extends HttpServlet {
    final static Logger logger = Logger.getLogger(FileReader.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + "start working");
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }
}
