package api.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class User extends BeanDomain<User>{

    protected String id;

    protected String userName;

    protected String password;

    protected String accountId;

    protected Set<String> roles = new TreeSet<String>();

    public User() {
    }

    public User(String accountId, String userName, String password, Set<String> roles) {
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        if (roles != null) {
            this.roles.addAll(roles);
        } else {
            this.roles.add("ROLE_USER");
        }
    }

    public boolean isNew() {
        return StringUtils.isEmpty(this.id);
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

    public Set<String> getRoles() {
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

    public void setRoles(Set<String> roles) {
        if (roles != null) {
            this.roles.clear();
            this.roles.addAll(roles);
        }
    }

    @Override
    public void merge(User user) {
        String accountId = user.getAccountId();
        String userName = user.getUserName();
        String userPassword = user.getPassword();
        Set<String> roles = user.getRoles();

        if (accountId != null) {
            setAccountId(accountId);
        }

        if (userName != null) {
            setUserName(userName);
        }
        if (userPassword != null) {
            setPassword(userPassword);
        }

        if (roles != null) {
            setRoles(roles);
        }
    }
}
