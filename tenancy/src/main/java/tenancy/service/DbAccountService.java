package tenancy.service;

import api.config.ApiConfig;
import api.domain.Account;
import api.domain.User;
import api.service.RoleService;
import api.service.UserService;
import api.type.DbType;
import api.domain.Role;
import api.service.AccountService;
import api.type.RoleType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;
import play.Logger;
import security.encoder.SecurityPasswordEncoder;
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
    UserService userService;

    @Inject
    RoleService roleService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        accountCollection.createIndex(new BasicDBObject("accountName", 1), new BasicDBObject("unique", true));
    }

    @Override
    public Account getAccount(String providerName, String accountName) {
        return accountRepository.findByProviderNameAndAccountName(providerName, accountName);
    }

    @Override
    public List<Account> getAccountsByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    @Override
    public Account getAccountById(String providerName, String accountId) {
        return accountRepository.findByProviderNameAndId(providerName, accountId);
    }

    @Override
    public Account createAccount(String providerName, String accountName, String dbType, String dbUri, String dbName, String superadminUserName, String superadminPassword) {
        Account account = new Account(providerName, accountName, dbType, dbUri, dbName, superadminUserName, superadminPassword, null);
        return saveAccount(providerName, account);
    }

    @Override
    public Account saveAccount(String providerName, Account account) {
        account.setProviderName(providerName);
        Account savedAccount = accountRepository.save(account);
        Set<String> roles = account.getRoles();
        if (!(roles != null && roles.size() == 1 && roles.contains(RoleType.ROLE_ACCOUNT_ELK.name()))) {
            refreshProviderSpringContexts(providerName);
            refreshAccount(savedAccount);
        }

        return savedAccount;
    }

    @Override
    public Long deleteAccount(String providerName, String accountName) {
        return accountRepository.deleteByProviderNameAndAccountName(providerName, accountName);
    }

    @Override
    public Long deleteAccounts(String providerName, Collection<String> accountIds) {
        return accountRepository.deleteByProviderNameAndIdIn(providerName, accountIds);
    }

    @Override
    public List<? extends Account> getAccounts(String providerName) {
        return accountRepository.findByProviderName(providerName);
    }

    private void refreshAccount(Account account) {
        String superadminUserName = account.getSuperadminUserName();
        String superadminPassword = account.getSuperadminInitialPassword();
        String accountName = account.getAccountName();
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
        User user = userService.getUser(accountName, superadminUserName);
        if (user == null) {
            userService.createUser(accountName, superadminUserName,
                    passwordEncoder.encode(superadminPassword), new TreeSet<String>(Arrays.asList(adminRole, userRole, superadminRole)));
        } else {
            userService.saveUser(accountName, user);
        }
    }

    @Override
    public void refreshProviderSpringContexts(String providerName) {
        List<? extends Account> accounts = getAccounts(providerName);
        Map<String, AnnotationConfigApplicationContext> tenantSpringContextMap = new HashMap<String, AnnotationConfigApplicationContext>();
        for (Account account : accounts) {
            AnnotationConfigApplicationContext context = createTenantSpringContext(account);
            tenantSpringContextMap.put(account.getAccountName(), context);
        }
        ApiConfig.tenantSpringContextMap = tenantSpringContextMap;
    }

    @Override
    public void refreshTenantSpringContexts(Account account) {
        AnnotationConfigApplicationContext context = createTenantSpringContext(account);
        Map<String, AnnotationConfigApplicationContext> tenantSpringContextMap = new HashMap<String, AnnotationConfigApplicationContext>();
        tenantSpringContextMap.put(account.getAccountName(), context);
        ApiConfig.tenantSpringContextMap = tenantSpringContextMap;
    }

    private AnnotationConfigApplicationContext createTenantSpringContext(Account account) {
        DbType tenantDbType = account.getDbType();
        String tenantConfigClassStr;
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
            return ApiConfig.createSpringContext(tenantConfigClassStr, createConfigurableEnvironment(account));
        } catch (Exception ex) {
            Logger.info("Cannot create tenant spring context", ex);
            return null;
        }
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
