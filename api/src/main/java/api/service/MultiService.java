package api.service;

import java.util.HashMap;

public interface MultiService {
    public UserService getCurrentTenantUserService();
    public RoleService getCurrentTenantRoleService();
}
