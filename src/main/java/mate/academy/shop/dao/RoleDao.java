package mate.academy.shop.dao;

import java.util.Optional;
import java.util.Set;
import mate.academy.shop.model.Role;

public interface RoleDao {
    Optional<Role> create(Role role);

    Optional<Role> get(Long id);

    Optional<Role> update(Role role);

    void delete(Role role);

    Set<Role> getAllRoleForUser(Long userId);

    void setRoleForUser(Long roleId, Long userId);

    void deleteRoleFromUser(Long roleId, Long userId);
}
