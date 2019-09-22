package mate.academy.shop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.User;

public interface UserDao {
    User create(User user);

    User get(Long id);

    List<User> getAllUsers();

    User update(User user);

    User delete(Long id);

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
