package mate.academy.shop.dao.impl;

import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.model.Order;
import mate.academy.shop.storage.Storage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Order get(Long orderId) {
        return Storage.orders.stream()
                .filter(x -> orderId.equals(x.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find order with id " + orderId));
    }

    @Override
    public List<Order> getByUser(Long userId) {
        return Storage.orders.stream()
                .filter(x -> userId.equals(x.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order update(Order order) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (order.getId().equals(Storage.orders.get(i))) {
                Storage.orders.set(i, order);
                return order;
            }
        }
        throw new NoSuchElementException("Can't find order with id " + order.getId());
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        Storage.orders.removeIf(x -> id.equals(x.getId()));
        return order;
    }
}
