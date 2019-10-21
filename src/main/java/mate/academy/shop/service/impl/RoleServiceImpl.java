package mate.academy.shop.service.impl;

import java.util.Set;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.anotation.Service;
import mate.academy.shop.dao.RoleDao;
import mate.academy.shop.model.Role;
import mate.academy.shop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Inject
    private static RoleDao roleDao;

    @Override
    public Set<Role> getAllRoleForUser(Long userId) {
        return roleDao.getAllRoleForUser(userId);
    }
}
