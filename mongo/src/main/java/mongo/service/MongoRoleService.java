package mongo.service;


import api.domain.Role;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.dao.MongoRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import api.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MongoRoleService implements RoleService {

    @Autowired
    MongoRoleRepository roleRepository;

    @Autowired
    DBCollection roleCollection;

    @PostConstruct
    public void init() {
        roleCollection.createIndex(new BasicDBObject("roleName", 1), new BasicDBObject("unique", true));
    }

    @Override
    public Role getRole(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void createRole(String roleName) {
        roleRepository.save(new Role(roleName));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
