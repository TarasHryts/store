package mate.academy.shop.dao.impl;

import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.model.User;
import mate.academy.shop.storage.Storage;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.util.List;
import java.util.NoSuchElementException;

@Dao
public class UserDaoImpl implements UserDao {
    final static Logger logger = Logger.getLogger(FileReader.class);

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
    public User delete(Long id) {
        User user = get(id);
        Storage.users.removeIf(x -> id.equals(x.getId()));
        return user;
    }
}
