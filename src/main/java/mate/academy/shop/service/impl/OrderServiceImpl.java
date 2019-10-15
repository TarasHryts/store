package mate.academy.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;
import mate.academy.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static UserDao userDao;
    @Inject
    private static ItemDao itemDao;
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Optional<Order> create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Optional<Order> get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public List<Order> getAllOrdersForUser(Long userId) {
        return orderDao.getByUser(userId);
    }

    @Override
    public Optional<Order> update(Order order) {
        return orderDao.update(order);
    }

    @Transactional
    @Override
    public Optional<Order> completeOrder(List<Item> items, Long userId) {
        List<Item> itemList = new ArrayList<>(items);
        Order order = new Order();

        order.setUser(userDao.get(userId).get());
        order.setId(orderDao.create(order).get().getId());
        Long bucketId = bucketDao.getBucketByUser(userId).get().getId();
        for (Item item : itemList) {
            orderDao.addItemToOrder(item.getId(), order.getId());
        }
        bucketDao.deleteAllItemsFromBucket(bucketId);
        return Optional.of(order);
    }

    @Override
    public Optional<Order> delete(Long id) {
        Order order = get(id).get();
        orderDao.delete(id);
        return Optional.of(order);
    }
}
