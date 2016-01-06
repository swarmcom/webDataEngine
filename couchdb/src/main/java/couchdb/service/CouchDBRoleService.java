package couchdb.service;

import api.domain.Role;
import api.service.RoleService;
import couchdb.dao.CouchDBRoleRepository;;
import couchdb.domain.CouchDBRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouchDBRoleService implements RoleService {

    @Autowired
    CouchDBRoleRepository roleRepository;

    @Override
    public Role getRole(String userName) {
        return roleRepository.findByRoleName(userName);
    }

    @Override
    public void createRole(String roleName) {
        roleRepository.create(new CouchDBRole(roleName));
    }

    @Override
    public List<? extends Role> getRoles() {
        return roleRepository.getAllRoles();
    }
}
