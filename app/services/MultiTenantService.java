package services;


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

    public UserService getCurrentTenantUserService() {
        return ApiConfig.tenantSpringContextMap.get(getCurrentTenantId()).getBean(UserService.class);
    }

    @Override
    public UserService getTenantUserService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(UserService.class);
    }

    public RoleService getCurrentTenantRoleService() {
        return ApiConfig.tenantSpringContextMap.get(getCurrentTenantId()).getBean(RoleService.class);
    }

    public RoleService getTenantRoleService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(RoleService.class);
    }

    private String getCurrentTenantId() {
        String accountId = TokenUtil.getAccountIdFromToken(SecurityContextHolder.getContext().getAuthentication());
        Logger.info("CurrentTenant: " + accountId);
        return accountId;
    }


}
