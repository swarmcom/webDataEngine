package tenancy.service;


import api.domain.User;
import api.service.MultiService;
import api.service.MultiUserService;
import api.service.UserService;
import org.springframework.stereotype.Component;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.Collection;
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
    public User createUser(String userName, String password, List<String> roles) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return createUser(currentAccountId, userName, password, roles);
    }

    @Override
    public User getUser(String userName) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getUser(currentAccountId, userName);
    }

    @Override
    public List<? extends User> getUsers() {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getUsers(currentAccountId);
    }

    @Override
    public User getUserById(String userId) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getUserById(currentAccountId, userId);
    }

    @Override
    public Long deleteUser(String userName) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return deleteUser(currentAccountId, userName);
    }

    @Override
    public Long deleteUsers(Collection<String> userIds) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return deleteUsers(currentAccountId, userIds);
    }

    @Override
    public User createUser(String tenantId, String userName, String password, List<String> roles) {
        UserService userService = multiService.getTenantUserService(tenantId);
        return userService.createUser(tenantId, userName, password, roles);
    }

    @Override
    public User saveUser(User user) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        if (user.isNew()) {
            user.setAccountId(currentAccountId);
        }
        UserService userService = multiService.getTenantUserService(currentAccountId);
        return userService.saveUser(user);
    }

    @Override
    public List<? extends User> getUsers(String accountId) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.getUsers(accountId);
    }

    @Override
    public User getUserById(String accountId, String userId) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.getUserById(accountId, userId);
    }

    @Override
    public Long deleteUser(String accountId, String userName) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.deleteUser(accountId, userName);
    }

    @Override
    public Long deleteUsers(String accountId, Collection<String> userIds) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.deleteUsers(accountId, userIds);
    }
}
