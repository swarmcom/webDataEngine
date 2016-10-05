package api.service;


public interface MultiService {
    public UserService getTenantUserService(String tenantId);
    public RoleService getTenantRoleService(String tenantId);
    public PhoneService getTenantPhoneService(String tenantId);
    public GatewayService getTenantGatewayService(String tenantId);
}
