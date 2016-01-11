package api.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

    protected String id;

    protected String userName;

    protected String password;

    protected String accountId;

    protected List<String> roles = new ArrayList<String>();

    public User() {
        userName = "Anonymous";
        password = "123";
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        roles.add("ROLE_USER");
    }

    public User(String userName, String password, List<String> roles) {
        this.userName = userName;
        this.password = password;
        if (roles != null) {
            this.roles.addAll(roles);
        } else {
            this.roles.add("ROLE_USER");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setRoles(List<String> roles) {
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }
}
