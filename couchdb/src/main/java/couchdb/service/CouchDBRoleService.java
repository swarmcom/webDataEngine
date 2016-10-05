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
    public Role getRole(String accountId, String userName) {
        return roleRepository.findByAccountIdAndRoleName(accountId, userName);
    }

    @Override
    public void createRole(String accountId, String roleName) {
        roleRepository.create(new CouchDBRole(accountId, roleName));
    }

    @Override
    public List<? extends Role> getRoles(String accountId) {
        return roleRepository.getAllRoles(accountId);
    }
}
