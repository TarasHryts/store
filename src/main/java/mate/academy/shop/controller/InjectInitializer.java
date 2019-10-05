package mate.academy.shop.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Injector;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.service.ItemService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(InjectInitializer.class);
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static RoleDao roleDao;
    @Inject
    private static BucketDao bucketDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.info(this.getClass().getName()
                    + "start working. Dependency injection started...");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
