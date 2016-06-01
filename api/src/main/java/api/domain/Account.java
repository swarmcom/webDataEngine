package api.domain;

import api.type.DbType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class Account extends BeanDomain<Account> {
    protected String id;
    protected String accountName;
    protected DbType dbType;
    protected String dbUri;
    protected String dbName;
    protected String superadminUserName;
    protected String superadminInitialPassword;

    public Account() {
    }

    public Account(String accountName) {
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = DbType.mongo;
        this.dbUri = "mongodb://localhost:27017/";
        this.dbName = "webDataEngine";
        this.superadminUserName = "superadmin";
        this.superadminInitialPassword = "123";
    }

    public Account(String accountName, String dbName) {
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = DbType.mongo;
        this.dbUri = "mongodb://localhost:27017/";
        this.dbName = (StringUtils.isEmpty(dbName) ? "webDataEngine" : dbName);
        this.superadminUserName = "superadmin";
        this.superadminInitialPassword = "123";
    }

    public Account(String accountName, String dbType, String dbUri, String dbName) {
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = (StringUtils.isEmpty(dbType) ? DbType.mongo: DbType.valueOf(dbType));
        this.dbUri = (StringUtils.isEmpty(dbUri) ? "mongodb://localhost:27017/" : dbUri);
        this.dbName = (StringUtils.isEmpty(dbName) ? "webDataEngine" : dbName);
        this.superadminUserName = "superadmin";
        this.superadminInitialPassword = "123";
    }

    public Account(String accountName, String dbType, String dbUri, String dbName, String superadminUserName, String superadminInitialPassword) {
        this.accountName = (StringUtils.isEmpty(accountName) ? "AnonymousTenant" : accountName);
        this.dbType = (StringUtils.isEmpty(dbType) ? DbType.mongo: DbType.valueOf(dbType) );
        this.dbUri = (StringUtils.isEmpty(dbUri) ? "mongodb://localhost:27017/" : dbUri);
        this.dbName = (StringUtils.isEmpty(dbName) ? "webDataEngine" : dbName);
        this.superadminUserName = (StringUtils.isEmpty(superadminUserName) ? "superadmin" : superadminUserName);
        this.superadminInitialPassword = (StringUtils.isEmpty(superadminInitialPassword) ? "123" : superadminInitialPassword);
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

    public void merge(Account account) {
        DbType type = account.getDbType();
        String dbUri = account.getDbUri();
        String accountName = account.getAccountName();
        String dbName = account.getDbName();
        String superadminUserName = account.getSuperadminUserName();
        String superadminInitialPassword = account.getSuperadminInitialPassword();

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

        if (superadminUserName != null) {
            setSuperadminUserName(superadminUserName);
        }

        if (superadminInitialPassword != null) {
            setSuperadminInitialPassword(superadminInitialPassword);
        }
    }
}
