package mate.academy.shop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.model.Order;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
        Locale.setDefault(Locale.US);
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (PreparedStatement statement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    Long orderId = generatedKey.getLong(1);
                    addOrderForUser(order.getUserId(), orderId);
                    return get(orderId);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't create the order for user with id=" + order.getUserId());
        }
        return null;
    }

    private void addOrderForUser(Long userId, Long orderId) {
        String query = "INSERT INTO users_orders (user_id, order_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add bucket for user with id=" + userId);
        }
    }

    @Override
    public Order get(Long orderId) {
        String query = "SELECT * FROM orders where order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long newOrderId = resultSet.getLong("order_id");
                Long newUserId = resultSet.getLong("user_id");
                Order order = new Order();
                order.setId(newOrderId);
                order.setUserId(newUserId);
                return order;
            }
        } catch (SQLException e) {
            logger.error("Can't get order by id=" + orderId);
        }
        return null;
    }

    @Override
    public List<Order> getByUser(Long userId) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT * FROM users_orders where user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long newOrderId = resultSet.getLong("order_id");
                list.add(get(newOrderId));
            }
        } catch (SQLException e) {
            logger.error("Can't find order for user with id=" + userId);
        }
        return list;
    }

    @Override
    public void addItemToOrder(Long itemId, Long orderId) {
        String query = "INSERT INTO items_orders (item_id, order_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, itemId);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add item to order with id=" + itemId);
        }
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET order_id=?, user_id=? WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            statement.setLong(2, order.getUserId());
            statement.setLong(3, order.getId());
            statement.executeUpdate();
            return order;
        } catch (SQLException e) {
            logger.error("Can't update the order with id=" + order.getId());
        }
        return null;
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        String query = "DELETE FROM orders WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return order;
        } catch (SQLException e) {
            logger.error("Can't delete the order with id=" + id);
        }
        return null;
    }
}
