package mate.academy.shop.service.impl;

import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;
import mate.academy.shop.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static UserDao userDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public List<Order> getAllOrdersForUser(Long userId) {
        return orderDao.getByUser(userId);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public Order completeOrder(List<Item> items, Long userId) {
        List<Item> itemList = new ArrayList<>(items);
        Order order = new Order(userId, items);
        orderDao.create(order);
        userDao.get(userId).getOrders().add(order);
        return order;
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        orderDao.delete(id);
        userDao.get(order.getUserId()).getOrders().remove(order);
        return order;
    }
}
