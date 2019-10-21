package mate.academy.shop.controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.User;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class AddToBucketController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddToBucketController.class);
    @Inject
    private static BucketService bucketService;
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = userService.get(userId).get();
        Bucket bucket = bucketService.getBucketByUser(userId).get();
        String itemId = req.getParameter("item_id");
        bucketService.addItem(bucket.getId(), Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
