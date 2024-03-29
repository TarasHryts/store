package mate.academy.shop.dao.hibernate;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.factory.HibernateUtil;
import mate.academy.shop.model.Item;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class ItemDaoHibernateImpl implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoHibernateImpl.class);

    @Override
    public Optional<Item> create(Item item) {
        Long itemId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            itemId = (long) session.save(item);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create the item with name=" + item.getName());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        item.setId(itemId);
        return Optional.of(item);
    }

    @Override
    public Optional<Item> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Item item = session.get(Item.class, id);
            return Optional.of(item);
        } catch (Exception e) {
            logger.error("Can't get item by id=" + id);
        }
        return null;
    }

    @Override
    public Optional<Item> update(Item item) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            logger.error("Can't update the item with id=" + item.getId());
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(item);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(get(id).get());
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete the item with id=" + id);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Item> getAllItems() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Item").list();
        } catch (Exception e) {
            logger.error("Can't get items");
        }
        return null;
    }
}
