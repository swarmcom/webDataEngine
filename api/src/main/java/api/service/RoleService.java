package api.service;

import api.domain.Role;
import api.domain.User;

import java.util.Collection;
import java.util.List;

public interface RoleService {
    Role getRole(String accountId, String roleName);

    void createRole(String accountId, String roleName);

    Role saveRole(String accountId, Role role);

    public List<? extends Role> getRoles(String accountId);

    Role getRoleById (String accountId, String roleId);

    Long deleteRole(String accountId, String roleName);

    Long deleteRoles(String accountId, Collection<String> roleIds);

    Long deleteRoles(String accountId);
}
