package api.service;


public interface MultiService {
    public UserService getCurrentTenantUserService();
    public UserService getTenantUserService(String tenantId);
    public RoleService getCurrentTenantRoleService();
    public RoleService getTenantRoleService(String tenantId);
}
