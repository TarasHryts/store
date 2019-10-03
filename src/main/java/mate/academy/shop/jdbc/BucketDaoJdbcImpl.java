package mate.academy.shop.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.model.Bucket;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final String BUCKET_ID_CONST = "bucket_id";
    private static final String USER_ID_CONST = "user_id";
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

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
            logger.warn("Can't create the bucket for user with id=" + bucket.getUserId());
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
            logger.warn("Can't add bucket for user with id=" + userId);
        }
    }

    public Bucket getBucketByUser(Long userId) {
        String query = "SELECT * FROM users_buckets where user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long newBucketId = resultSet.getLong(BUCKET_ID_CONST);
                return get(newBucketId);
            }
        } catch (SQLException e) {
            logger.warn("Can't find bucket for user with id=" + userId);
        }
        return null;
    }

    @Override
    public Bucket get(Long bucketId) {
        String query = "SELECT * FROM buckets where bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long newBucketId = resultSet.getLong(BUCKET_ID_CONST);
                Long newUserId = resultSet.getLong(USER_ID_CONST);
                Bucket bucket = new Bucket();
                bucket.setId(newBucketId);
                bucket.setUserId(newUserId);
                return bucket;
            }
        } catch (SQLException e) {
            logger.warn("Can't get bucket by id=" + bucketId);
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
            logger.warn("Can't update the bucket with id=" + bucket.getId());
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
            logger.warn("Can't delete the bucket with id=" + id);
        }
        return bucket;
    }
}
