package mate.academy.shop.controller;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.service.BucketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteItemFromBucket extends HttpServlet {
    private static final Long DEFAULT_BUCKET = 0L;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        bucketService.deleteItem(DEFAULT_BUCKET, Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
