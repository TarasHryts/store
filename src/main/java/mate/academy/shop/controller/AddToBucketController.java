package mate.academy.shop.controller;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.service.BucketService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

public class AddToBucketController extends HttpServlet {
    final static Logger logger = Logger.getLogger(FileReader.class);
    private static final Long DEFAULT_BUCKET = 0L;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + "start working");
        Bucket bucket = bucketService.get(DEFAULT_BUCKET);
        String itemId = req.getParameter("item_id");
        bucketService.addItem(DEFAULT_BUCKET, Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
