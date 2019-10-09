package mate.academy.shop.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.dao.hibernate.BucketDaoHibernateImpl;
import mate.academy.shop.dao.hibernate.ItemDaoHibernateImpl;
import mate.academy.shop.dao.hibernate.RoleDaoHibernateImpl;
import mate.academy.shop.dao.hibernate.UserDaoHibernateImpl;
import mate.academy.shop.dao.jdbc.OrderDaoJdbcImpl;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.ItemService;
import mate.academy.shop.service.OrderService;
import mate.academy.shop.service.UserService;
import mate.academy.shop.service.impl.BucketServiceImpl;
import mate.academy.shop.service.impl.ItemServiceImpl;
import mate.academy.shop.service.impl.OrderServiceImpl;
import mate.academy.shop.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

public class Factory {
    private static Logger logger = Logger.getLogger(Factory.class);
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/newdatabase?"
                    + "user=root&password=729313&serverTimezone=UTC");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.error("Can't establish connection to our DB", e);
        }
    }

    private static BucketDao bucketDao;
    private static ItemDao itemDao;
    private static OrderDao orderDao;
    private static UserDao userDao;
    private static RoleDao roleDao;

    private static BucketService bucketService;
    private static ItemService itemService;
    private static OrderService orderService;
    private static UserService userService;

    public static RoleDao getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDaoHibernateImpl();
        }
        return roleDao;
    }

    public static BucketDao getBucketDao() {
        if (bucketDao == null) {
            bucketDao = new BucketDaoHibernateImpl();
        }
        return bucketDao;
    }

    public static ItemDao getItemDao() {
        if (itemDao == null) {
            itemDao = new ItemDaoHibernateImpl();
        }
        return itemDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            orderDao = new OrderDaoJdbcImpl(connection);
        }
        return orderDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoHibernateImpl();
        }
        return userDao;
    }

    public static BucketService getBucketService() {
        if (bucketService == null) {
            bucketService = new BucketServiceImpl();
        }
        return bucketService;
    }

    public static ItemService getItemService() {
        if (itemService == null) {
            itemService = new ItemServiceImpl();
        }
        return itemService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
