package mate.academy.shop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.User;

public interface UserDao {
    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAllUsers();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
