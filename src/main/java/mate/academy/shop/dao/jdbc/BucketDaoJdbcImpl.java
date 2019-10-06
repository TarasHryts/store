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
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final String BUCKET_ID_COLUMN = "bucket_id";
    private static final String USER_ID_COLUMN = "user_id";
    private static Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
        Locale.setDefault(Locale.US);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String query = "INSERT INTO buckets (user_id) VALUES (?);";
        try (PreparedStatement statement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bucket.getUserId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long bucketId = generatedKeys.getLong(1);
                    addBucketForUser(bucket.getUserId(), bucketId);
                    return get(bucketId);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't create the bucket for user with id=" + bucket.getUserId());
        }
        return bucket;
    }

    public void addBucketForUser(Long userId, Long bucketId) {
        String query = "INSERT INTO users_buckets (user_id, bucket_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, bucketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add bucket for user with id=" + userId);
        }
    }

    public Bucket getBucketByUser(Long userId) {
        String query = "SELECT * FROM users_buckets where user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long newBucketId = resultSet.getLong(BUCKET_ID_COLUMN);
                return get(newBucketId);
            }
        } catch (SQLException e) {
            logger.error("Can't find bucket for user with id=" + userId);
        }
        return null;
    }

    @Override
    public void addItemToBucket(Long itemId, Long bucketId) {
        String query = "INSERT INTO items_buckets (item_id, bucket_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, itemId);
            statement.setLong(2, bucketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add item to bucket with id=" + itemId);
        }
    }

    @Override
    public List<Item> getItemForBucket(Long bucketId) {
        List<Item> list = new ArrayList<>();
        String query = "SELECT items.item_id, items.name, items.price FROM items "
                + "INNER JOIN items_buckets ON items.item_id = items_buckets.item_id "
                + "WHERE items_buckets.bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long itemId = resultSet.getLong("item_id");
                String itemName = resultSet.getString("name");
                Double itemPrice = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(itemName);
                item.setPrice(itemPrice);
                list.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't find items for bucket with id=" + bucketId);
        }
        return list;
    }

    @Override
    public void deleteAllItemsFromBucket(Long bucketId) {
        String query = "DELETE FROM items_buckets WHERE bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete all items from bucket with id=" + bucketId);
        }
    }

    @Override
    public void deleteItemFromBucket(Long bucketId, Long itemId) {
        String query = "DELETE FROM items_buckets WHERE bucket_id=? AND item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete all items from bucket with id=" + bucketId);
        }
    }

    @Override
    public Bucket get(Long bucketId) {
        String query = "SELECT * FROM buckets where bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long newBucketId = resultSet.getLong(BUCKET_ID_COLUMN);
                Long newUserId = resultSet.getLong(USER_ID_COLUMN);
                Bucket bucket = new Bucket();
                bucket.setId(newBucketId);
                bucket.setUserId(newUserId);
                bucket.setItems(getItemForBucket(bucketId));
                return bucket;
            }
        } catch (SQLException e) {
            logger.error("Can't get bucket by id=" + bucketId);
        }
        return null;
    }

    @Override
    public Bucket update(Bucket bucket) {
        String query = "UPDATE buckets SET bucket_id=?, user_id=? WHERE bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getId());
            statement.setLong(2, bucket.getUserId());
            statement.setLong(3, bucket.getId());
            statement.executeUpdate();
            return bucket;
        } catch (SQLException e) {
            logger.error("Can't update the bucket with id=" + bucket.getId());
        }
        return null;
    }

    @Override
    public Bucket delete(Long id) {
        Bucket bucket = get(id);
        String query = "DELETE FROM buckets WHERE bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return bucket;
        } catch (SQLException e) {
            logger.error("Can't delete the bucket with id=" + id);
        }
        return bucket;
    }
}
