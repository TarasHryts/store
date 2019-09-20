package mate.academy.shop.dao;

import mate.academy.shop.model.Order;

import java.util.List;

public interface OrderDao {
    Order create(Order order);

    Order get(Long orderId);

    List<Order> getByUser(Long userId);

    Order update(Order order);

    Order delete(Long id);
}
