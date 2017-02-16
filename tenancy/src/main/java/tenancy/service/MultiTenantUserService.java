package tenancy.service;


import api.domain.User;
import api.service.MultiService;
import api.service.UserService;
import org.springframework.stereotype.Component;
import play.Logger;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class MultiTenantUserService implements UserService {
    @Inject
    MultiService multiService;

    @Override
    public User getUser(String accountId, String userName) {
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.getUser(accountId, userName);
    }

    @Override
    public User createUser(String tenantId, String userName, String password, Set<String> roles) {
        UserService userService = multiService.getTenantUserService(tenantId);
        return userService.createUser(tenantId, userName, password, roles);
    }

    @Override
    public User saveUser(String accountId, User user) {
        if (user.isNew()) {
            user.setAccountId(accountId);
        }
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.saveUser(accountId, user);
    }

    @Override
    public List<? extends User> getUsers(String accountId) {
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.getUsers(accountId);
    }

    @Override
    public User getUserById(String accountId, String userId) {
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.getUserById(accountId, userId);
    }

    @Override
    public Long deleteUser(String accountId, String userName) {
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.deleteUser(accountId, userName);
    }

    @Override
    public Long deleteUsers(String accountId, Collection<String> userIds) {
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.deleteUsers(accountId, userIds);
    }

    @Override
    public Long deleteUsers(String accountId) {
        UserService userService = multiService.getTenantUserService(accountId);
        return userService.deleteUsers(accountId);
    }
}
