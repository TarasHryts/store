package mate.academy.shop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.model.Order;

public interface OrderDao {
    Optional<Order> create(Order order);

    Optional<Order> get(Long orderId);

    List<Order> getByUser(Long userId);

    Optional<Order> update(Order order);

    Optional<Order> delete(Long id);

    void addItemToOrder(Long itemId, Long orderId);
}
