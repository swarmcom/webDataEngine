package api.service;

import api.domain.Role;

public interface MultiRoleService extends RoleService {
    void createRole(String tenantId, String roleName);
    Role getRole(String tenantId, String roleName);
}
