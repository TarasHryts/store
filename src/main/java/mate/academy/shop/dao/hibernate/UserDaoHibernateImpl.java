package mate.academy.shop.dao.hibernate;

import java.util.List;
import java.util.Optional;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.exceptions.AuthenticationException;
import mate.academy.shop.factory.HibernateUtil;
import mate.academy.shop.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoHibernateImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoHibernateImpl.class);

    @Override
    public Optional<User> create(User user) {
        Long userId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            userId = (Long) session.save(user);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create the user with name=" + user.getName(), e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        user.setId(userId);
        return Optional.of(user);
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.of(user);
        } catch (Exception e) {
            logger.error("Can't get user by id=" + id, e);
        }
        return null;
    }

    @Override
    public Optional<User> update(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.getTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update the user with id=" + user.getId(), e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(user);
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
            logger.error("Can't delete the user with id=" + id, e);
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
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User").list();
        } catch (Exception e) {
            logger.error("Can't get users", e);
        }
        return null;
    }

    @Override
    public Optional<User> login(String login, String password)
            throws AuthenticationException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User WHERE login=:login");
            query.setParameter("login", login);
            User user = (User) query.stream().findFirst().get();
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        } catch (Exception e) {
            logger.error("Incorrect username or password", e);
        }
        return null;
    }

    @Override
    public Optional<User> getByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User WHERE token=:token");
            query.setParameter("token", token);
            List<User> list = query.list();
            return list.stream().findFirst();
        } catch (Exception e) {
            logger.error("Can't get user with token=" + token, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User WHERE login=:login");
            return query.setParameter("login", login).stream().findFirst();
        } catch (Exception e) {
            logger.error("Can't get user with token=" + login, e);
        }
        return Optional.empty();
    }
}
