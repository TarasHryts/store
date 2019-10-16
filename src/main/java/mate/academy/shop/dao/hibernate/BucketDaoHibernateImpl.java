package mate.academy.shop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.dao.BucketDao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.factory.HibernateUtil;
import mate.academy.shop.model.Bucket;
import mate.academy.shop.model.Item;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class BucketDaoHibernateImpl implements BucketDao {
    private static final Logger logger = Logger.getLogger(BucketDaoHibernateImpl.class);
    @Inject
    private static ItemDao itemDao;

    @Override
    public Optional<Bucket> create(Bucket bucket) {
        Long bucketId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            bucketId = (Long) session.save(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create the bucket for user with id=" + bucket.getUser().getId());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        bucket.setId(bucketId);
        return Optional.of(bucket);
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = session.get(Bucket.class, bucketId);
            return Optional.of(bucket);
        } catch (Exception e) {
            logger.error("Can't get bucket by id=" + bucketId);
        }
        return null;
    }

    @Override
    public Optional<Bucket> update(Bucket bucket) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update the bucket with id=" + bucket.getId());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(bucket);
    }

    @Override
    public Optional<Bucket> delete(Long id) {
        Transaction transaction = null;
        Bucket bucket = get(id).get();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete the bucket with id=" + id);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(bucket);
    }

    @Override
    public void addBucketForUser(Long userId, Long bucketId) {
    }

    @Override
    public Optional<Bucket> getBucketByUser(Long userId) {
        Bucket bucket = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Bucket WHERE user_id =: userId");
            query.setParameter("userId", userId);
            bucket = (Bucket) query.list().stream().findFirst().get();
        }
        return Optional.of(bucket);
    }

    @Override
    public void addItemToBucket(Long itemId, Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = get(bucketId).get();
            List<Item> itemList = bucket.getItems();
            itemList.add(itemDao.get(itemId).get());
            bucket.setItems(itemList);
            update(bucket);
        }
    }

    @Override
    public List<Item> getItemsFromBucket(Long bucketId) {
        Bucket bucket = get(bucketId).get();
        return bucket.getItems();
    }

    @Override
    public void deleteAllItemsFromBucket(Long bucketId) {
        Bucket bucket = get(bucketId).get();
        bucket.setItems(new ArrayList<Item>());
        update(bucket);
    }

    @Override
    public void deleteItemFromBucket(Long bucketId, Long itemId) {
        Bucket bucket = get(bucketId).get();
        Item item = itemDao.get(itemId).get();
        List<Item> itemList = bucket.getItems();
        itemList.remove(item);
        bucket.setItems(itemList);
        update(bucket);
    }
}
