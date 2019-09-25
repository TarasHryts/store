package mate.academy.shop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;

public interface UserService {
    User create(User user);

    User get(Long id);

    List<Order> getAllOrders(Long userId);

    User update(User user);

    User delete(Long id);

    List<User> getAll();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
