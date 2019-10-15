package mate.academy.shop.dao.hibernate;

import java.util.Optional;
import java.util.Set;
import mate.academy.shop.anotation.Dao;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.dao.UserDao;
import mate.academy.shop.factory.HibernateUtil;
import mate.academy.shop.model.Role;
import mate.academy.shop.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class RoleDaoHibernateImpl implements RoleDao {
    private static Logger logger = Logger.getLogger(RoleDaoHibernateImpl.class);
    @Inject
    private static UserDao userDao;

    @Override
    public Optional<Role> create(Role role) {
        Long roleId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            roleId = (long) session.save(role);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Cat't create the role with name=" + role.getRoleName());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        role.setId(roleId);
        return Optional.of(role);
    }

    @Override
    public Optional<Role> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Role role = session.get(Role.class, id);
            return Optional.of(role);
        } catch (Exception e) {
            logger.error("Cat't get role by id=" + id);
        }
        return null;
    }

    @Override
    public Optional<Role> update(Role role) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update the role with id=" + role.getId());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.of(role);
    }

    @Override
    public void delete(Role role) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(role);
        } catch (Exception e) {
            logger.error("Can't delete the role with id=" + role.getId());
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
    public Set<Role> getAllRoleForUser(Long userId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            return user.getRoles();
        } catch (Exception e) {
            logger.error("Can't find roles for user with id=" + userId);
        }
        return null;
    }

    @Override
    public void setRoleForUser(Long roleId, Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = userDao.get(userId).get();
            Set<Role> roleSet = user.getRoles();
            roleSet.add(get(roleId).get());
            user.setRoles(roleSet);
            userDao.update(user);
        } catch (Exception e) {
            logger.error("Can't find user with id=" + userId);
        }
    }

    @Override
    public void deleteRoleFromUser(Long roleId, Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = userDao.get(userId).get();
            Set<Role> roleSet = user.getRoles();
            roleSet.remove(get(roleId).get());
            user.setRoles(roleSet);
        } catch (Exception e) {
            logger.error("Can't delete role with id=" + roleId);
        }
    }
}
