package services;

import api.domain.Role;
import api.service.MultiRoleService;
import api.service.MultiService;
import api.service.RoleService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class MultiTenantRoleService implements MultiRoleService {

    @Inject
    MultiService multiService;

    @Override
    public Role getRole(String roleName) {
        RoleService currentRoleService = multiService.getCurrentTenantRoleService();
        return currentRoleService.getRole(roleName);
    }

    @Override
    public void createRole(String roleName) {
        RoleService currentRoleService = multiService.getCurrentTenantRoleService();
        currentRoleService.createRole(roleName);
    }

    @Override
    public List<? extends Role> getRoles() {
        RoleService currentRoleService = multiService.getCurrentTenantRoleService();
        return currentRoleService.getRoles();
    }
}
