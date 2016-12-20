package tenancy.service;

import api.domain.Role;
import api.service.MultiService;
import api.service.RoleService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Component
public class MultiTenantRoleService implements RoleService {

    @Inject
    MultiService multiService;

    @Override
    public List<? extends Role> getRoles(String accountId) {
        RoleService currentRoleService = multiService.getTenantRoleService(accountId);
        return currentRoleService.getRoles(accountId);
    }

    @Override
    public Role getRoleById(String accountId, String roleId) {
        RoleService roleService = multiService.getTenantRoleService(accountId);
        return roleService.getRoleById(accountId, roleId);
    }

    @Override
    public Long deleteRole(String accountId, String roleName) {
        RoleService userService = multiService.getTenantRoleService(accountId);
        return userService.deleteRole(accountId, roleName);
    }

    @Override
    public Long deleteRoles(String accountId, Collection<String> roleIds) {
        RoleService roleService = multiService.getTenantRoleService(accountId);
        return roleService.deleteRoles(accountId, roleIds);
    }

    @Override
    public Long deleteRoles(String accountId) {
        RoleService roleService = multiService.getTenantRoleService(accountId);
        return roleService.deleteRoles(accountId);
    }

    @Override
    public void createRole(String tenantId, String roleName) {
        RoleService roleService = multiService.getTenantRoleService(tenantId);
        roleService.createRole(tenantId, roleName);
    }

    @Override
    public Role saveRole(String accountId, Role role) {
        RoleService roleService = multiService.getTenantRoleService(accountId);
        return roleService.saveRole(accountId, role);
    }

    @Override
    public Role getRole(String tenantId, String roleName) {
        RoleService roleService = multiService.getTenantRoleService(tenantId);
        return roleService.getRole(tenantId, roleName);
    }
}
