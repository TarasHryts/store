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
    private static final String USER_ID_CONST = "user_id";
    private static final String USER_NAME_CONST = "name";
    private static final String USER_SURNAME_CONST = "surname";
    private static final String USER_LOGIN_CONST = "login";
    private static final String USER_PASSWORD_CONST = "password";
    private static final String USER_TOKEN_CONST = "token";
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, surname, login, password, token) "
                + "VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long userId = generatedKeys.getLong(1);
                    return get(userId);
                }
            }
        } catch (SQLException e) {
            logger.warn("Can't create the user with name=" + user.getName());
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
                Long userId = resultSet.getLong(USER_ID_CONST);
                String name = resultSet.getString(USER_NAME_CONST);
                String surname = resultSet.getString(USER_SURNAME_CONST);
                String login = resultSet.getString(USER_LOGIN_CONST);
                String password = resultSet.getString(USER_PASSWORD_CONST);
                String token = resultSet.getString(USER_TOKEN_CONST);
                User user = new User(userId);
                user.setName(name);
                user.setSurname(surname);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                return user;
            }
        } catch (SQLException e) {
            logger.warn("Can't get user by id=" + id);
        }
        return null;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name=?, surname=?, login=?, password=?, token=? "
                + "WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            logger.warn("Can't update the user with id=" + user.getId());
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
            logger.warn("Can't delete the user with id=" + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long userId = resultSet.getLong(USER_ID_CONST);
                String name = resultSet.getString(USER_NAME_CONST);
                String surname = resultSet.getString(USER_SURNAME_CONST);
                String login = resultSet.getString(USER_LOGIN_CONST);
                String password = resultSet.getString(USER_PASSWORD_CONST);
                String token = resultSet.getString(USER_TOKEN_CONST);
                User user = new User(userId);
                user.setName(name);
                user.setSurname(surname);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                list.add(user);
            }
        } catch (SQLException e) {
            logger.warn("Can't get users");
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
                long userId = resultSet.getLong(USER_ID_CONST);
                String name = resultSet.getString(USER_NAME_CONST);
                String surname = resultSet.getString(USER_SURNAME_CONST);
                String token = resultSet.getString(USER_TOKEN_CONST);
                User user = new User(userId);
                user.setName(name);
                user.setSurname(surname);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                return user;
            }
        } catch (SQLException e) {
            logger.warn("Incorrect username or password");
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
                Long userId = resultSet.getLong(USER_ID_CONST);
                String name = resultSet.getString(USER_NAME_CONST);
                String surname = resultSet.getString(USER_SURNAME_CONST);
                String login = resultSet.getString(USER_LOGIN_CONST);
                String password = resultSet.getString(USER_PASSWORD_CONST);
                String tokenUsers = resultSet.getString(USER_TOKEN_CONST);
                User user = new User(userId);
                user.setName(name);
                user.setSurname(surname);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(tokenUsers);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.warn("Can't get user with token=" + token);
        }
        return Optional.empty();
    }
}
