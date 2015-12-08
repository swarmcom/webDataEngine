package models.domain;

import api.domain.Role;

public class ModelRole implements Role {

    private String roleName;

    public ModelRole() {

    }

    public ModelRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
