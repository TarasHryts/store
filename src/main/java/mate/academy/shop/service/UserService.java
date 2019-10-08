package mate.academy.shop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;

public interface UserService {
    Optional<User> create(User user);

    Optional<User> get(Long id);

    List<Order> getAllOrders(Long userId);

    Optional<User> update(User user);

    void delete(Long id);

    List<User> getAll();

    Optional<User> login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);

    Optional<User> getByLogin(String login);
}
