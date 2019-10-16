package mate.academy.shop.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Injector;
import mate.academy.shop.factory.Util;
import mate.academy.shop.model.Role;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(InjectInitializer.class);
    @Inject
    private static UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.info(this.getClass().getName()
                    + "start working. Dependency injection started...");
            Injector.injectDependency();
//            if (userService.get(1L).isPresent()) {
//                User admin = new User();
//                admin.addRole(Role.of("ADMIN"));
//                admin.setLogin("ADMIN");
//                admin.setPassword(Util.hashPassword("ADMIN", admin.getSalt()));
//                userService.create(admin);
//                User user = new User();
//                user.addRole(Role.of("USER"));
//                user.setLogin("USER");
//                user.setPassword(Util.hashPassword("USER", user.getSalt()));
//                userService.create(user);
//            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
