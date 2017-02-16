package api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BeanDomain<User>{

    protected String id;

    protected String userName;

    protected String password;

    protected String accountId;

    protected Set<String> roles = new TreeSet<String>();

    protected Boolean digestEncoded;

    protected Date birthDate;

    protected Map<String, Map<String, Object>> settings = new HashMap<String, Map<String, Object>>();

    protected Boolean suspended;

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

    public Boolean isDigestEncoded() {
        return digestEncoded;
    }

    public void setDigestEncoded(Boolean digestEncoded) {
        this.digestEncoded = digestEncoded;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public Boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Map<String, Map<String, Object>> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Map<String, Object>> settings) {
        this.settings = settings;
    }

    @Override
    public void merge(User user) {
        String accountId = user.getAccountId();
        String userName = user.getUserName();
        Set<String> roles = user.getRoles();
        Boolean digestEncoded = user.isDigestEncoded();
        Date birthDate = user.getBirthDate();
        Boolean suspended = user.isSuspended();

        if (accountId != null) {
            setAccountId(accountId);
        }

        if (userName != null) {
            setUserName(userName);
        }

        if (roles != null) {
            setRoles(roles);
        }

        if (digestEncoded != null) {
            setDigestEncoded(digestEncoded);
        }

        if (birthDate != null) {
            setBirthDate(birthDate);
        }

        if (suspended != null) {
            setSuspended(suspended);
        }

        mergeSettings(user.getSettings(), this.settings);
    }
}
