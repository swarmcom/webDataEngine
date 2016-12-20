package api.domain;


public class Role extends BeanDomain<Role>{

    protected String id;

    protected String accountId;

    protected String roleName;

    public Role() {
        this.roleName = "ROLE_ADMIN";
    }

    public Role(String accountId, String roleName) {
        this.accountId = accountId;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public void merge(Role role) {
        String accountId = role.getAccountId();
        String roleName = role.getRoleName();

        if (accountId != null) {
            setAccountId(accountId);
        }

        if (roleName != null) {
            setRoleName(roleName);
        }
    }
}
