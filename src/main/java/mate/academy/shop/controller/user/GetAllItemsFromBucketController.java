package mate.academy.shop.controller.user;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Item;
import mate.academy.shop.service.BucketService;
import org.apache.log4j.Logger;

public class GetAllItemsFromBucketController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetAllItemsFromBucketController.class);
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        Long bucketId = bucketService.getBucketByUser(userId).get().getId();
        List<Item> items = bucketService.getAllItems(bucketId);
        req.setAttribute("items", items);
        req.getRequestDispatcher("/WEB-INF/views/bucket.jsp").forward(req, resp);
    }
}
