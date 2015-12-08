package mongo.service;


import api.domain.Role;
import mongo.dao.MongoRoleRepository;
import mongo.domain.MongoRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.service.RoleService;

import java.util.List;

@Component
public class MongoRoleService implements RoleService {

    @Autowired
    MongoRoleRepository roleRepository;

    @Override
    public Role getRole(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void createRole(String roleName) {
        roleRepository.save(new MongoRole(roleName));
    }

    @Override
    public List<? extends Role> getRoles() {
        return roleRepository.findAll();
    }
}
