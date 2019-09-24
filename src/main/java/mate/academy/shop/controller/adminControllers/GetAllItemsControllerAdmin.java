package mate.academy.shop.controller.adminControllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Item;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.ItemService;
import org.apache.log4j.Logger;

public class GetAllItemsControllerAdmin extends HttpServlet {
    private final static Logger logger = Logger.getLogger(GetAllItemsControllerAdmin.class);
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        List<Item> items = itemService.getAll();
        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/allItemsAdmin.jsp").forward(req, resp);
    }
}
