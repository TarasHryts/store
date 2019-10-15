package mate.academy.shop.dao.hibernate;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.dao.ItemDao;
import mate.academy.shop.dao.OrderDao;
import mate.academy.shop.factory.HibernateUtil;
import mate.academy.shop.model.Item;
import mate.academy.shop.model.Order;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoHibernateImpl implements OrderDao {
    private static Logger logger = Logger.getLogger(OrderDaoHibernateImpl.class);
    @Inject
    private static ItemDao itemDao;

    @Override
    public Optional<Order> create(Order order) {
        Long orderId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            orderId = (Long) session.save(order);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create the order for user with id=" + order.getUser().getId());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        order.setId(orderId);
        return Optional.of(order);
    }

    @Override
    public Optional<Order> get(Long orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, orderId);
            return Optional.of(order);
        } catch (Exception e) {
            logger.error("Can't get order by id=" + orderId);
        }
        return null;
    }

    @Override
    public List<Order> getByUser(Long userId) {
        List<Order> orderList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Order WHERE user_id =: userId");
            query.setParameter("userId", userId);
            orderList = query.list();
        } catch (Exception e) {
            logger.error("Can't find order for user with id=" + userId);
        }
        return orderList;
    }

    @Override
    public Optional<Order> update(Order order) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update the order with id=" + order.getId());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(order);
    }

    @Override
    public Optional<Order> delete(Long id) {
        Transaction transaction = null;
        Order order = get(id).get();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(order);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete the order with id=" + id);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(order);
    }

    @Override
    public void addItemToOrder(Long itemId, Long orderId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = get(orderId).get();
            List<Item> itemList = order.getItems();
            itemList.add(itemDao.get(itemId).get());
            order.setItems(itemList);
            update(order);
        }
    }
}
