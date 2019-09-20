package mate.academy.shop.service;

import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User get(Long id);

    List<Order> getAllOrders(Long userId);

    User update(User user);

    User delete(Long id);

    List<User> getAll();
}
