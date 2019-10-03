package mate.academy.shop.dao;

import java.util.List;
import mate.academy.shop.model.Order;

public interface OrderDao {
    Order create(Order order);

    Order get(Long orderId);

    List<Order> getByUser(Long userId);

    Order update(Order order);

    Order delete(Long id);

    void addItemToOrder(Long itemId, Long orderId);
}
