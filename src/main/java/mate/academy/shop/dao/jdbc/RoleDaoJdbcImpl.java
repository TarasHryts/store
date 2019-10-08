package mate.academy.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.model.Role;
import org.apache.log4j.Logger;

@Dao
public class RoleDaoJdbcImpl extends AbstractDao<Role> implements RoleDao {
    private static final String ROLE_ID_COLUMN = "role_id";
    private static final String ROLE_NAME_COLUMN = "name";
    private static Logger logger = Logger.getLogger(RoleDaoJdbcImpl.class);

    public RoleDaoJdbcImpl(Connection connection) {
        super(connection);
        Locale.setDefault(Locale.US);
    }

    @Override
    public Optional<Role> create(Role role) {
        String query = "INSERT INTO roles (name) VALUES (?);";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, role.getRoleName().getName());
            statement.executeUpdate();
            return Optional.of(role);
        } catch (SQLException e) {
            logger.error("Cat't create the role with name=" + role.getRoleName());
        }
        return null;
    }

    @Override
    public Optional<Role> get(Long id) {
        String query = "SELECT * FROM newdatabase.roles where role_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong(ROLE_ID_COLUMN);
                String roleName = resultSet.getString(ROLE_NAME_COLUMN);
                Role role = Role.of(roleName);
                role.setId(roleId);
                return Optional.of(role);
            }
        } catch (SQLException e) {
            logger.error("Cat't get role by id=" + id);
        }
        return null;
    }

    @Override
    public Optional<Role> update(Role role) {
        String query = "UPDATE roles SET role_id=?, name=? WHERE role_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, role.getId());
            statement.setString(2, role.getRoleName().getName());
            statement.setLong(3, role.getId());
            statement.executeUpdate();
            return Optional.of(role);
        } catch (SQLException e) {
            logger.error("Can't update the role with id=" + role.getId());
        }
        return null;
    }

    @Override
    public void delete(Role role) {
        String query = "DELETE FROM roles WHERE role_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, role.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete the role with id=" + role.getId());
        }
    }

    @Override
    public Set<Role> getAllRoleForUser(Long userId) {
        Set<Role> roleSet = new HashSet<>();
        String query = "SELECT * FROM users_roles where user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong(ROLE_ID_COLUMN);
                roleSet.add(get(roleId).get());
            }
        } catch (SQLException e) {
            logger.error("Can't find roles for user with id=" + userId);
        }
        return roleSet;
    }

    @Override
    public void setRoleForUser(Long roleId, Long userId) {
        String query = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't find user with id=" + userId);
        }
    }

    @Override
    public void deleteRoleFromUser(Long roleId, Long userId) {
        String query = "DELETE FROM users_roles WHERE user_id=? AND role_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete role with id=" + roleId);
        }
    }
}
