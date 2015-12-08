package api.service;

import api.domain.Role;
import api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class InitService {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostConstruct
    public void init() {
        User user = userService.getUser("superadmin");
        if (user == null) {
            userService.createUser("superadmin", "123", new ArrayList<String>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
        }
        Role roleAdmin = roleService.getRole("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleService.createRole("ROLE_ADMIN");
        }
        Role roleUser = roleService.getRole("ROLE_USER");
        if (roleUser == null) {
            roleService.createRole("ROLE_USER");
        }
    }
}
