package mate.academy.shop.dao;

import mate.academy.shop.model.User;

import java.util.List;

public interface UserDao {
    User create(User user);

    User get(Long id);

    List<User> getAllUsers();

    User update(User user);

    User delete(Long id);
}
