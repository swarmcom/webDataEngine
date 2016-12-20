package mongo.service;


import api.domain.Role;
import api.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.dao.MongoRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MongoRoleService implements RoleService {

    @Autowired
    MongoRoleRepository roleRepository;

    @Autowired
    DBCollection roleCollection;

    @PostConstruct
    public void init() {
        Map indexMap = new HashMap();
        indexMap.put("roleName", 1);
        indexMap.put("accountId", 1);
        roleCollection.createIndex(new BasicDBObject(indexMap), new BasicDBObject("unique", true));
    }

    @Override
    public Role getRole(String accountId, String roleName) {
        return roleRepository.findByAccountIdAndRoleName(accountId, roleName);
    }

    @Override
    public void createRole(String accountId, String roleName) {
        roleRepository.save(new Role(accountId, roleName));
    }

    @Override
    public Role saveRole(String accountId, Role role) {
        role.setAccountId(accountId);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles(String accountId) {
        return roleRepository.findByAccountId(accountId);
    }

    @Override
    public Role getRoleById(String accountId, String roleId) {
        return roleRepository.findByAccountIdAndId(accountId, roleId);
    }

    @Override
    public Long deleteRole(String accountId, String roleName) {
        return roleRepository.deleteByAccountIdAndRoleName(accountId, roleName);
    }

    @Override
    public Long deleteRoles(String accountId, Collection<String> roleIds) {
        return roleRepository.deleteByAccountIdAndIdIn(accountId, roleIds);
    }

    @Override
    public Long deleteRoles(String accountId) {
        return roleRepository.deleteByAccountId(accountId);
    }
}
