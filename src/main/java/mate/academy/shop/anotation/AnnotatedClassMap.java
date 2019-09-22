package mate.academy.shop.anotation;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.factory.Factory;
import mate.academy.shop.service.BucketService;
import mate.academy.shop.service.ItemService;
import mate.academy.shop.service.OrderService;
import mate.academy.shop.service.UserService;
import org.apache.log4j.Logger;

public class AnnotatedClassMap {
    final static Logger logger = Logger.getLogger(FileReader.class);
    private static Map<Class, Object> classMap = new HashMap<>();

    static {
        logger.info("Adding data do factory");
        classMap.put(BucketDao.class, Factory.getBucketDao());
        classMap.put(ItemDao.class, Factory.getItemDao());
        classMap.put(OrderDao.class, Factory.getOrderDao());
        classMap.put(UserDao.class, Factory.getUserDao());
        classMap.put(BucketService.class, Factory.getBucketService());
        classMap.put(ItemService.class, Factory.getItemService());
        classMap.put(OrderService.class, Factory.getOrderService());
        classMap.put(UserService.class, Factory.getUserService());
        logger.info("Data successfully added");
    }

    public static Object getImplementation(Class interfaceClass) {
        return classMap.get(interfaceClass);
    }
}
