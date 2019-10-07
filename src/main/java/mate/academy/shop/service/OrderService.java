package mate.academy.shop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;

public interface OrderService {
    Optional<Order> create(Order order);

    Optional<Order> get(Long id);

    List<Order> getAllOrdersForUser(Long userId);

    Optional<Order> update(Order order);

    Optional<Order> completeOrder(List<Item> items, Long userId);

    Optional<Order> delete(Long id);
}
