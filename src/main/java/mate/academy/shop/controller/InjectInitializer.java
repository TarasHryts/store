package mate.academy.shop.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Injector;
import mate.academy.shop.model.Role;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    private final static Logger logger = Logger.getLogger(InjectInitializer.class);
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

        User admin = new User();
        admin.setName("Ostap");
        admin.setSurname("Bender");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("1");
        userService.create(admin);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
