package tenancy.service;


import api.config.ApiConfig;
import api.service.*;
import org.springframework.stereotype.Component;
import security.util.TokenUtil;

@Component
public class MultiTenantService implements MultiService {

    @Override
    public UserService getTenantUserService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(UserService.class);
    }

    @Override
    public UserService getCurrentTenantUserService() {
        return getTenantUserService(TokenUtil.getCurrentAccountId());
    }

    @Override
    public RoleService getTenantRoleService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(RoleService.class);
    }

    @Override
    public PhoneService getCurrentTenantPhoneService() {
        return ApiConfig.tenantSpringContextMap.get(TokenUtil.getCurrentAccountId()).getBean(PhoneService.class);
    }

    @Override
    public PhoneService getTenantPhoneService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(PhoneService.class);
    }

    @Override
    public GatewayService getCurrentTenantGatewayService() {
        return ApiConfig.tenantSpringContextMap.get(TokenUtil.getCurrentAccountId()).getBean(GatewayService.class);
    }

    @Override
    public GatewayService getTenantGatewayService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(GatewayService.class);
    }

    @Override
    public RoleService getCurrentTenantRoleService() {
        return getTenantRoleService(TokenUtil.getCurrentAccountId());
    }
}
