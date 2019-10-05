package mate.academy.shop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.User;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

@Dao
public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User create(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public User get(Long id) {
        return Storage.users.stream()
                .filter(x -> id.equals(x.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (user.getId().equals(Storage.users.get(i))) {
                Storage.users.set(i, user);
                return user;
            }
        }
        logger.error("Can't find user with id " + user.getId());
        throw new NoSuchElementException("Can't find user with id " + user.getId());
    }

    @Override
    public void delete(Long id) {
        User user = get(id);
        Storage.users.removeIf(x -> id.equals(x.getId()));
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = Storage.users.stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst();
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new AuthenticationException("incorrect username or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(x -> x.getToken().equals(token))
                .findFirst();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.empty();
    }
}
