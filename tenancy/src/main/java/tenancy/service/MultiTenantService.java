package tenancy.service;


import api.config.ApiConfig;
import api.service.*;
import org.springframework.stereotype.Component;
import play.Logger;

@Component
public class MultiTenantService implements MultiService {

    @Override
    public UserService getTenantUserService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(UserService.class);
    }

    @Override
    public RoleService getTenantRoleService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(RoleService.class);
    }

    @Override
    public PhoneService getTenantPhoneService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(PhoneService.class);
    }

    @Override
    public GatewayService getTenantGatewayService(String tenantId) {
        return ApiConfig.tenantSpringContextMap.get(tenantId).getBean(GatewayService.class);
    }
}
