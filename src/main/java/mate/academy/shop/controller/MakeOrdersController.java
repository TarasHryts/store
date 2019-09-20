package mate.academy.shop.controller;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MakeOrdersController extends HttpServlet {
    private static final Long DEFAULT_BUCKET = 0L;

    @Inject
    private static OrderService orderService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = bucketService.getAllItems(DEFAULT_BUCKET);
        Order order = orderService.completeOrder(items, DEFAULT_BUCKET);
        bucketService.getAllItems(DEFAULT_BUCKET).clear();
        resp.sendRedirect(req.getContextPath() + "/servlet/showAllOrders");
    }
}
