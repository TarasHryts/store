package mate.academy.shop.controller.user;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;
import mate.academy.shop.service.OrderService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class DeleteOrderController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteOrderController.class);
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        List<Order> ordersTest = orderService.getAllOrdersForUser(userId);
        String orderId = req.getParameter("order_id");
        orderService.delete(Long.valueOf(orderId));
        List<Order> orders = orderService.getAllOrdersForUser(userId);
        User user = userService.get(userId).get();
        req.setAttribute("orders", orders);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/showAllOrders.jsp").forward(req, resp);
    }
}
