package mate.academy.shop.controller;

import java.io.FileReader;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Injector;
import mate.academy.shop.model.Item;
import mate.academy.shop.service.ItemService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    final static Logger logger = Logger.getLogger(FileReader.class);
    @Inject
    private static ItemService itemService;
    @Inject
    private static UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.info(this.getClass().getName()
                    + "start working. Dependency injection started...");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        List<Item> items = itemService.getAll();
        items.add(new Item("Mersedes", 200D));
        items.add(new Item("Moskvich", 1D));
        items.add(new Item("Opel", 58D));
        items.add(new Item("BMW", 2210D));
        items.add(new Item("Reno", 50D));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
