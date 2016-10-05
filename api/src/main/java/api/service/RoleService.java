package api.service;

import api.domain.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String accountId, String roleName);

    void createRole(String accountId, String roleName);

    public List<? extends Role> getRoles(String accountId);
}
