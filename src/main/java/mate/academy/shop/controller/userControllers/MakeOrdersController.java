package mate.academy.shop.controller.userControllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.OrderService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class MakeOrdersController extends HttpServlet {
    private final static Logger logger = Logger.getLogger(MakeOrdersController.class);
    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        Long bucketId = userService.get(userId).getBucket().getId();
        List<Item> items = bucketService.getAllItems(bucketId);
        Order order = orderService.completeOrder(items, userId);
        bucketService.getAllItems(bucketId).clear();
        resp.sendRedirect(req.getContextPath() + "/servlet/showAllOrders");
    }
}
