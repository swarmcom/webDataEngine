package api.service;

import api.domain.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String roleName);

    void createRole(String roleName);

    public List<? extends Role> getRoles();
}
