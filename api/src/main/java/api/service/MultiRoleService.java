package api.service;

public interface MultiRoleService extends RoleService {
    void createRole(String tenantId, String roleName);
}
