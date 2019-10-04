package mate.academy.shop.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.model.User;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static final String USER_ID_COLUMN = "user_id";
    private static final String USER_NAME_COLUMN = "name";
    private static final String USER_SURNAME_COLUMN = "surname";
    private static final String USER_LOGIN_COLUMN = "login";
    private static final String USER_PASSWORD_COLUMN = "password";
    private static final String USER_TOKEN_COLUMN = "token";
    private static final String USER_SALT_COLUMN = "salt";
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, surname, login, password, token, salt) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.setBytes(6, user.getSalt());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long userId = generatedKeys.getLong(1);
                    return get(userId);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't create the user with name=" + user.getName());
        }
        return null;
    }

    @Override
    public User get(Long id) {
        String query = "SELECT * FROM users where user_id=?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return new User(resultSet.getLong(USER_ID_COLUMN),
                        resultSet.getString(USER_NAME_COLUMN),
                        resultSet.getString(USER_SURNAME_COLUMN),
                        resultSet.getString(USER_LOGIN_COLUMN),
                        resultSet.getString(USER_PASSWORD_COLUMN),
                        resultSet.getBytes(USER_SALT_COLUMN),
                        resultSet.getString(USER_TOKEN_COLUMN));
            }
        } catch (SQLException e) {
            logger.error("Can't get user by id=" + id);
        }
        return null;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name=?, surname=?, login=?, password=?, token=?, salt=? "
                + "WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.setBytes(6, user.getSalt());
            statement.setLong(7, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            logger.error("Can't update the user with id=" + user.getId());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete the user with id=" + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(new User(resultSet.getLong(USER_ID_COLUMN),
                        resultSet.getString(USER_NAME_COLUMN),
                        resultSet.getString(USER_SURNAME_COLUMN),
                        resultSet.getString(USER_LOGIN_COLUMN),
                        resultSet.getString(USER_PASSWORD_COLUMN),
                        resultSet.getBytes(USER_SALT_COLUMN),
                        resultSet.getString(USER_TOKEN_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Can't get users");
        }
        return list;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        String query = "SELECT * FROM users WHERE login=? AND password=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong(USER_ID_COLUMN);
                String name = resultSet.getString(USER_NAME_COLUMN);
                String surname = resultSet.getString(USER_SURNAME_COLUMN);
                String token = resultSet.getString(USER_TOKEN_COLUMN);
                byte[] salt = resultSet.getBytes(USER_SALT_COLUMN);
                User user = new User(userId);
                user.setSalt(salt);
                user.setName(name);
                user.setSurname(surname);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                return user;
            }
        } catch (SQLException e) {
            logger.error("Incorrect username or password");
        }
        return null;
    }

    @Override
    public Optional<User> getByToken(String token) {
        String query = "SELECT * FROM users WHERE token=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(new User(resultSet.getLong(USER_ID_COLUMN),
                        resultSet.getString(USER_NAME_COLUMN),
                        resultSet.getString(USER_SURNAME_COLUMN),
                        resultSet.getString(USER_LOGIN_COLUMN),
                        resultSet.getString(USER_PASSWORD_COLUMN),
                        resultSet.getBytes(USER_SALT_COLUMN),
                        resultSet.getString(USER_TOKEN_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Can't get user with token=" + token);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        String query = "SELECT * FROM users WHERE login=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(new User(resultSet.getLong(USER_ID_COLUMN),
                        resultSet.getString(USER_NAME_COLUMN),
                        resultSet.getString(USER_SURNAME_COLUMN),
                        resultSet.getString(USER_LOGIN_COLUMN),
                        resultSet.getString(USER_PASSWORD_COLUMN),
                        resultSet.getBytes(USER_SALT_COLUMN),
                        resultSet.getString(USER_TOKEN_COLUMN)));
            }
        } catch (SQLException e) {
            logger.error("Can't get user with token=" + login);
        }
        return Optional.empty();
    }
}
