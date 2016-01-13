package tenancy.service;


import api.config.ApiConfig;
import api.service.MultiService;
import api.service.RoleService;
import api.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Logger;
import security.util.TokenUtil;

@Component
public class MultiTenantService implements MultiService {

    @Override
    public UserService getTenantUserService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(UserService.class);
    }

    @Override
    public UserService getCurrentTenantUserService() {
        return getTenantUserService(getCurrentTenantId());
    }

    @Override
    public RoleService getTenantRoleService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(RoleService.class);
    }

    @Override
    public RoleService getCurrentTenantRoleService() {
        return getTenantRoleService(getCurrentTenantId());
    }

    private String getCurrentTenantId() {
        String accountId = TokenUtil.getAccountIdFromToken(SecurityContextHolder.getContext().getAuthentication());
        Logger.info("CurrentTenant: " + accountId);
        return accountId;
    }


}
