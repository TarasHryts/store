package mate.academy.shop.controller.adminControllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.service.ItemService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class DeleteItemAdmin extends HttpServlet {
    private final static Logger logger = Logger.getLogger(DeleteItemAdmin.class);

    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info(this.getClass().getName() + " start working");
        String itemId = req.getParameter("item_id");
        itemService.delete(Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/allItemsAdmin");
    }
}
