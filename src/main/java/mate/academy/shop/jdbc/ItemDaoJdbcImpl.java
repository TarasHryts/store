package mate.academy.shop.jdbc;

import java.sql.Connection;
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
    private static final String DB_NAME = "newdatabase";

    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
        Locale.setDefault(Locale.US);
    }

    @Override
    public Item create(Item item) {
        Statement stmt = null;
        String query = String
                .format("INSERT INTO %s.items (%s, %s) VALUES ('%s', '%.4f');",
                        DB_NAME, ITEM_NAME_CONST, ITEM_PRICE_CONST,
                        item.getName(), item.getPrice());
        try {
            stmt = connection.createStatement();
            stmt.executeQuery(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't create the item with name=" + item.getName());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        Statement stmt = null;
        String query = String.format("SELECT * FROM %s.items where item_id=%d;", DB_NAME, id);
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong(ITEM_ID_CONST);
                String name = rs.getString(ITEM_NAME_CONST);
                double price = rs.getDouble(ITEM_PRICE_CONST);
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id=" + id);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        Statement stmt = null;
        String query = String
                .format("UPDATE %s.items SET item_id=%d, name='%s', price=%.4f WHERE item_id=%d;",
                        DB_NAME, item.getId(), item.getName(), item.getPrice(), item.getId());
        try {
            stmt = connection.createStatement();
            stmt.executeQuery(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't update the item with id=" + item.getId());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item delete(Long id) {
        Statement stmt = null;
        Item item = get(id);
        String query = String
                .format("DELETE FROM %s.items WHERE item_id=%d;", DB_NAME, id);
        try {
            stmt = connection.createStatement();
            stmt.executeQuery(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't create the item with id=" + id);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        Statement stmt = null;
        String query = String.format("SELECT * FROM %s.items;", DB_NAME);
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong(ITEM_ID_CONST);
                String name = rs.getString(ITEM_NAME_CONST);
                double price = rs.getDouble(ITEM_PRICE_CONST);
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                list.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get items");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return list;
    }
}
