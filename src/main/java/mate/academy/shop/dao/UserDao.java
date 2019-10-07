package mate.academy.shop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.User;

public interface UserDao {
    Optional<User> create(User user);

    Optional<User> get(Long id);

    Optional<User> update(User user);

    void delete(Long id);

    List<User> getAllUsers();

    Optional<User> login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);

    Optional<User> getByLogin(String login);
}
