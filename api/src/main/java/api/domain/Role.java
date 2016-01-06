package api.domain;


public class Role {

    protected String id;

    protected String roleName;

    public Role() {
        this.roleName = "ROLE_ADMIN";
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
