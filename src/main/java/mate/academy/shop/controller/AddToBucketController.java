package mate.academy.shop.controller;

import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.User;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.ItemService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class AddToBucketController extends HttpServlet {
    final static Logger logger = Logger.getLogger(FileReader.class);

    @Inject
    private static BucketService bucketService;
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = userService.get(userId);
        Bucket bucket = user.getBucket();
        String itemId = req.getParameter("item_id");
        bucketService.addItem(bucket.getId(), Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
