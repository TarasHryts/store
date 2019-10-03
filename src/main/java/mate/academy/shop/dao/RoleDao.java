package mate.academy.shop.dao;

import java.util.Set;
import mate.academy.shop.model.Role;

public interface RoleDao {
    Role create(Role role);

    Role get(Long id);

    Role update(Role role);

    void delete(Role role);

    Set<Role> getAllRoleForUser(Long userId);

    void setRoleForUser(Long roleId, Long userId);

    void deleteRoleFromUser(Long roleId, Long userId);
}
