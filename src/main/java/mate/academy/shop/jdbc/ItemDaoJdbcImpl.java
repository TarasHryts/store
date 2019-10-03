package mate.academy.shop.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final String ITEM_ID_CONST = "item_id";
    private static final String ITEM_NAME_CONST = "name";
    private static final String ITEM_PRICE_CONST = "price";

    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
        Locale.setDefault(Locale.US);
    }

    @Override
    public Item create(Item item) {
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";

        try (PreparedStatement statement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long itemId = generatedKeys.getLong(1);
                    return get(itemId);
                }
            }
        } catch (SQLException e) {
            logger.warn("Can't create the item with name=" + item.getName());
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        String query = "SELECT * FROM items where item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long itemId = resultSet.getLong(ITEM_ID_CONST);
                String name = resultSet.getString(ITEM_NAME_CONST);
                double price = resultSet.getDouble(ITEM_PRICE_CONST);
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id=" + id);
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        String query = "UPDATE items SET item_id=?, name=?, price=? WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, item.getId());
            statement.setString(2, item.getName());
            statement.setDouble(3, item.getPrice());
            statement.setLong(4, item.getId());
            statement.executeUpdate();
            return item;
        } catch (SQLException e) {
            logger.warn("Can't update the item with id=" + item.getId());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM items WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete the item with id=" + id);
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        String query = "SELECT * FROM items;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long itemId = resultSet.getLong(ITEM_ID_CONST);
                String name = resultSet.getString(ITEM_NAME_CONST);
                double price = resultSet.getDouble(ITEM_PRICE_CONST);
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                list.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get items");
        }
        return list;
    }
}
