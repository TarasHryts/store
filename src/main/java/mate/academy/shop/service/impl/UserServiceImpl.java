package mate.academy.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return userDao.get(userId).getOrders();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public User delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        return userDao.login(login, password);
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.getByToken(token);
    }
}
