package mate.academy.shop.service;

import java.util.Set;
import mate.academy.shop.model.Role;

public interface RoleService {
    Set<Role> getAllRoleForUser(Long userId);
}
