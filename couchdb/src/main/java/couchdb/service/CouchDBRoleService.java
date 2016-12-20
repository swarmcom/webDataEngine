package couchdb.service;

import api.domain.Role;
import api.service.RoleService;
import couchdb.dao.CouchDBRoleRepository;;
import couchdb.domain.CouchDBRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
    public Role saveRole(String accountId, Role role) {
        return null;
    }

    @Override
    public List<? extends Role> getRoles(String accountId) {
        return roleRepository.getAllRoles(accountId);
    }

    @Override
    public Role getRoleById(String accountId, String roleId) {
        return null;
    }

    @Override
    public Long deleteRole(String accountId, String roleName) {
        return null;
    }

    @Override
    public Long deleteRoles(String accountId, Collection<String> roleIds) {
        return null;
    }

    @Override
    public Long deleteRoles(String accountId) {
        return null;
    }
}
