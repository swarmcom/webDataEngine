package tenancy.service;


import api.domain.User;
import api.service.MultiService;
import api.service.MultiUserService;
import api.service.UserService;
import org.springframework.stereotype.Component;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.List;

@Component
public class MultiTenantUserService implements MultiUserService {
    @Inject
    MultiService multiService;

    @Override
    public User getUser(String accountId, String userName) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.getUser(accountId, userName);
    }

    @Override
    public void createUser(String userName, String password, List<String> roles) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        createUser(currentAccountId, userName, password, roles);
    }

    @Override
    public void createUser(String tenantId, String userName, String password, List<String> roles) {
        UserService userService = multiService.getTenantUserService(tenantId);
        userService.createUser(tenantId, userName, password, roles);
    }

    @Override
    public List<? extends User> getUsers(String accountId) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.getUsers(accountId);
    }
}
