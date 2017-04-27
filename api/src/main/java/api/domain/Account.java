package api.domain;

import api.type.DbType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import play.Logger;

import java.util.Set;
import java.util.TreeSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account extends BeanDomain<Account> {
    protected String id;
    protected String accountName;
    protected String description;
    protected String email;
    protected String companyName;
    protected DbType dbType;
    protected String dbUri;
    protected String dbName;
    protected String superadminUserName;
    protected String superadminInitialPassword;
    protected String providerName;
    protected Boolean suspended;
    protected Set<String> roles;


    public Account() {
    }

    public Account(String providerName, String accountName) {
        this.providerName = providerName;
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = DbType.mongo;
        this.dbUri = "mongodb://localhost:27017/";
        this.dbName = "webDataEngine";
        this.superadminUserName = "superadmin";
        this.superadminInitialPassword = "123";
    }

    public Account(String providerName, String accountName, String dbName) {
        this.providerName = providerName;
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = DbType.mongo;
        this.dbUri = "mongodb://localhost:27017/";
        this.dbName = (StringUtils.isEmpty(dbName) ? "webDataEngine" : dbName);
        this.superadminUserName = "superadmin";
        this.superadminInitialPassword = "123";
    }

    public Account(String providerName, String accountName, String dbType, String dbUri, String dbName) {
        this.providerName = providerName;
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = (StringUtils.isEmpty(dbType) ? DbType.mongo: DbType.valueOf(dbType));
        this.dbUri = (StringUtils.isEmpty(dbUri) ? "mongodb://localhost:27017/" : dbUri);
        this.dbName = (StringUtils.isEmpty(dbName) ? "webDataEngine" : dbName);
        this.superadminUserName = "superadmin";
        this.superadminInitialPassword = "123";
    }

    public Account(String providerName, String accountName, String dbType, String dbUri, String dbName, String superadminUserName, String superadminInitialPassword, Set<String> roles) {
        this.providerName = providerName;
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = (StringUtils.isEmpty(dbType) ? DbType.mongo: DbType.valueOf(dbType) );
        this.dbUri = (StringUtils.isEmpty(dbUri) ? "mongodb://localhost:27017/" : dbUri);
        this.dbName = (StringUtils.isEmpty(dbName) ? "webDataEngine" : dbName);
        this.superadminUserName = (StringUtils.isEmpty(superadminUserName) ? "superadmin" : superadminUserName);
        this.superadminInitialPassword = (StringUtils.isEmpty(superadminInitialPassword) ? "123" : superadminInitialPassword);
        if (roles != null) {
            this.roles = new TreeSet<String>();
            this.roles.addAll(roles);
        } else {
            this.roles.add("ROLE_ACCOUNT_ADMIN");
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

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public String getDbUri() {
        return dbUri;
    }

    public void setDbUri(String dbUri) {
        this.dbUri = dbUri;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSuperadminUserName() {
        return superadminUserName;
    }

    public void setSuperadminUserName(String superadminUserName) {
        this.superadminUserName = superadminUserName;
    }

    public String getSuperadminInitialPassword() {
        return superadminInitialPassword;
    }

    public void setSuperadminInitialPassword(String superadminInitialPassword) {
        this.superadminInitialPassword = superadminInitialPassword;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        if (roles != null) {
            this.roles = new TreeSet<String>();
            this.roles.addAll(roles);
        }
    }

    public void merge(Account account) {
        DbType type = account.getDbType();
        String dbUri = account.getDbUri();
        String accountName = account.getAccountName();
        String dbName = account.getDbName();
        String superadminUserName = account.getSuperadminUserName();
        String superadminInitialPassword = account.getSuperadminInitialPassword();
        String providerName = account.getProviderName();
        String description = account.getDescription();
        String email = account.getEmail();
        String companyName = account.getCompanyName();
        Boolean suspended = account.isSuspended();
        Set<String> roles = account.getRoles();

        if (type != null) {
            setDbType(type);
        }

        if (dbUri != null) {
            setDbUri(dbUri);
        }
        if (accountName != null) {
            setAccountName(accountName);
        }

        if (dbName != null) {
            setDbName(dbName);
        }

        if (roles != null) {
            setRoles(roles);
        }

        if (superadminUserName != null) {
            setSuperadminUserName(superadminUserName);
        }

        if (superadminInitialPassword != null) {
            setSuperadminInitialPassword(superadminInitialPassword);
        }

        if (providerName != null) {
            setProviderName(providerName);
        }

        if (description != null) {
            setDescription(description);
        }

        if (email != null) {
            setEmail(email);
        }

        if (companyName != null) {
            setCompanyName(companyName);
        }

        if (suspended != null) {
            setSuspended(suspended);
        }
    }
}
