package mate.academy.shop.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.Order;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Long DEFAULT_ROLE = 58L;
    @Inject
    private static UserDao userDao;
    @Inject
    private static RoleDao roleDao;

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User create(User user) {
        user.setToken(getToken());
        User newUser = userDao.create(user);
        roleDao.setRoleForUser(DEFAULT_ROLE, newUser.getId());
        newUser.setRoles(roleDao.getAllRoleForUser(newUser.getId()));
        return newUser;
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
    public void delete(Long id) {
        userDao.delete(id);
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

    @Override
    public String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512 ");
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x",b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword.toString();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userDao.getByLogin(login);
    }

}
