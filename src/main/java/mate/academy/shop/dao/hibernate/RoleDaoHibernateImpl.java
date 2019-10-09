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
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class RoleDaoHibernateImpl implements RoleDao {
    @Inject
    private static UserDao userDao;

    @Override
    public Optional<Role> create(Role role) {
        Long roleId = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            roleId = (long) session.save(role);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        role.setId(roleId);
        return Optional.of(role);
    }

    @Override
    public Optional<Role> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Role role = session.get(Role.class, id);
            return Optional.of(role);
        }
    }

    @Override
    public Optional<Role> update(Role role) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.of(role);
    }

    @Override
    public void delete(Role role) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.delete(role);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Set<Role> getAllRoleForUser(Long userId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            User user = session.get(User.class, userId);
            return user.getRoles();
        }
    }

    @Override
    public void setRoleForUser(Long roleId, Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            User user = userDao.get(userId).get();
            Set<Role> roleSet = user.getRoles();
            roleSet.add(get(roleId).get());
            user.setRoles(roleSet);
            userDao.update(user);
        }
    }

    @Override
    public void deleteRoleFromUser(Long roleId, Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            User user = userDao.get(userId).get();
            Set<Role> roleSet = user.getRoles();
            roleSet.remove(get(roleId).get());
            user.setRoles(roleSet);
        }
    }
}
