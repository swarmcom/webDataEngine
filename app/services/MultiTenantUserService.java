package services;


import api.domain.User;
import api.service.MultiService;
import api.service.MultiUserService;
import api.service.UserService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class MultiTenantUserService implements MultiUserService {
    @Inject
    MultiService multiService;

    @Override
    public User getUser(String userName) {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.getUser(userName);
    }

    @Override
    public void createUser(String userName, String password, List<String> roles) {
        UserService userService = multiService.getCurrentTenantUserService();
        userService.createUser(userName, password, roles);
    }

    @Override
    public List<? extends User> getUsers() {
        UserService userService = multiService.getCurrentTenantUserService();
        return userService.getUsers();
    }
}
