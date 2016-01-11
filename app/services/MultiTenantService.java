package services;


import api.config.ApiConfig;
import api.service.MultiService;
import api.service.RoleService;
import api.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import play.Logger;
import security.token.ClientToken;
import security.token.ClientType;
import security.util.TokenUtil;

import java.util.*;

@Component
public class MultiTenantService implements MultiService {

    public UserService getCurrentTenantUserService() {
        return ApiConfig.tenantSpringContextMap.get(getCurrentTenantId()).getBean(UserService.class);
    }

    public RoleService getCurrentTenantRoleService() {
        return ApiConfig.tenantSpringContextMap.get(getCurrentTenantId()).getBean(RoleService.class);
    }

    private String getCurrentTenantId() {
        String accountId = TokenUtil.getAccountIdFromToken(SecurityContextHolder.getContext().getAuthentication());
        Logger.info("CurrentTenant: " + accountId);
        return accountId;
    }


}
