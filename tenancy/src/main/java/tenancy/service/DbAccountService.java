package tenancy.service;

import api.config.ApiConfig;
import api.domain.Account;
import api.type.DbType;
import api.domain.Role;
import api.service.AccountService;
import api.service.MultiRoleService;
import api.service.MultiUserService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import play.Logger;
import security.encoder.SecurityPasswordEncoder;
import security.util.EncoderUtil;
import tenancy.dao.DbAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

@Component
public class DbAccountService implements AccountService {

    @Autowired
    DbAccountRepository accountRepository;

    @Autowired
    DBCollection accountCollection;

    @Inject
    MultiUserService userService;

    @Inject
    MultiRoleService roleService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    private HashMap<String, AnnotationConfigApplicationContext> tenantSpringContextMap = new HashMap<String, AnnotationConfigApplicationContext>();

    @PostConstruct
    public void init() {
        accountCollection.createIndex(new BasicDBObject("accountName", 1), new BasicDBObject("unique", true));
        refreshTenantSpringContexts();
    }

    @Override
    public Account getAccount(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    @Override
    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Account createAccount(String accountName, String dbType, String dbUri, String dbName, String superadminUserName, String superadminPassword) {
        Account savedAccount = accountRepository.save(new Account(accountName, dbType, dbUri, dbName, superadminUserName, superadminPassword));
        refreshTenantSpringContexts();
        String adminRole = "ROLE_ADMIN";
        String userRole = "ROLE_USER";
        String superadminRole = "ROLE_SUPERADMIN";
        Role existingRole = roleService.getRole(accountName, adminRole);
        if (existingRole == null) {
            roleService.createRole(accountName, adminRole);
        }
        existingRole = roleService.getRole(accountName, userRole);
        if (existingRole == null) {
            roleService.createRole(accountName, userRole);
        }
        existingRole = roleService.getRole(accountName, superadminRole);
        if (existingRole == null) {
            roleService.createRole(accountName, superadminRole);
        }
        userService.createUser(accountName, superadminUserName,
                passwordEncoder.encode(superadminPassword), new TreeSet<String>(Arrays.asList(adminRole, userRole, superadminRole)));
        return savedAccount;
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Long deleteAccount(String accountName) {
        return accountRepository.deleteByAccountName(accountName);
    }

    @Override
    public Long deleteAccounts(Collection<String> accountIds) {
        return accountRepository.deleteByIdIn(accountIds);
    }

    @Override
    public List<? extends Account> getAccounts() {
        return accountRepository.findAll();
    }

    private void refreshTenantSpringContexts() {
        List<? extends Account> accounts = getAccounts();
        String tenantId;
        DbType tenantDbType;
        String tenantConfigClassStr;
        AnnotationConfigApplicationContext tenantSpringContext;
        for (Account account : accounts) {
            tenantId = account.getAccountName();
            tenantDbType = account.getDbType();
            switch (tenantDbType) {
                case mongo: {
                    tenantConfigClassStr = "mongo.config.MongoConfig";
                    break;
                }
                case couchDB: {
                    tenantConfigClassStr = "couchdb.config.CouchdbConfig";
                    break;
                }
                default: {
                    tenantConfigClassStr = "mongo.config.MongoConfig";
                }
            }
            try {
                tenantSpringContext = ApiConfig.createSpringContext(tenantConfigClassStr, createConfigurableEnvironment(account));
                tenantSpringContextMap.put(tenantId, tenantSpringContext);
            } catch (Exception ex) {
                Logger.info("Cannot create tenant spring context", ex);
            }
        }
        ApiConfig.tenantSpringContextMap = tenantSpringContextMap;
    }

    private ConfigurableEnvironment createConfigurableEnvironment(Account account) {
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        Map myMap = new HashMap();
        DbType dbType = account.getDbType();
        switch (dbType) {
            case mongo: {
                putMongoProperties(myMap, account);
                break;
            }
            case couchDB: {
                myMap.put("uri", account.getDbUri());
                break;
            }
            default: {
                putMongoProperties(myMap, account);
            }
        }
        propertySources.addFirst(new MapPropertySource("account_properties", myMap));
        return environment;
    }

    private void putMongoProperties(Map myMap, Account account) {
        myMap.put("dbname", account.getDbName());
        myMap.put("uri", account.getDbUri());
    }
}
