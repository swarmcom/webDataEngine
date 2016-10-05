package tenancy.service;

import api.domain.Role;
import api.service.MultiService;
import api.service.RoleService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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
    public void createRole(String tenantId, String roleName) {
        RoleService roleService = multiService.getTenantRoleService(tenantId);
        roleService.createRole(tenantId, roleName);
    }

    @Override
    public Role getRole(String tenantId, String roleName) {
        RoleService roleService = multiService.getTenantRoleService(tenantId);
        return roleService.getRole(tenantId, roleName);
    }
}
