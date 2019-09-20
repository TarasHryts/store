package mate.academy.shop.service;

import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order get(Long id);

    List<Order> getAllOrdersForUser(Long userId);

    Order update(Order order);

    Order completeOrder(List<Item> items, Long userId);

    Order delete(Long id);
}
