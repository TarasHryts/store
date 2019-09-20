package mate.academy.shop.controller;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;
import mate.academy.shop.service.OrderService;
import mate.academy.shop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteOrderController extends HttpServlet {
    private static final Long DEFAULT_USER = 0L;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> ordersTest = orderService.getAllOrdersForUser(DEFAULT_USER);
        String orderId = req.getParameter("order_id");
        orderService.delete(Long.valueOf(orderId));
        List<Order> orders = orderService.getAllOrdersForUser(DEFAULT_USER);
        User user = userService.get(DEFAULT_USER);
        req.setAttribute("orders", orders);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/showAllOrders.jsp").forward(req, resp);
    }
}
