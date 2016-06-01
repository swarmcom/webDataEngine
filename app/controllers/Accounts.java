package controllers;

import api.domain.Account;
import api.domain.BeanDomain;
import api.service.AccountService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.DigestAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import play.Logger;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@DigestAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
public class Accounts extends BaseController {
    @Inject
    AccountService accountService;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Account accountToAdd = new Account();
        mergeDefaults(accountToAdd, getAccountsDefaultsJSON());
        merge(accountToAdd);
        Logger.info("MIRCEA " + accountToAdd.getDbName());
        Account savedAccount = accountService.saveAccount(accountToAdd);
        return savedAccount;
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return accountService.getAccount(name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return accountService.getAccountById(id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return accountService.deleteAccount(name);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return accountService.deleteAccounts(idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Account existingAccount = accountService.getAccount(name);
        merge(existingAccount);
        return (existingAccount != null ? accountService.saveAccount(existingAccount) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Account existingAccount = accountService.getAccountById(id);
        merge(existingAccount);
        return (existingAccount != null ? accountService.saveAccount(existingAccount) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return accountService.getAccounts();
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Account> accounts = accountService.getAccounts();
        ArrayNode node = Json.newArray();
        for (Account account : accounts) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(account.getId());
            itemNode.add(account.getAccountName());
            itemNode.add(account.getDbName());
            itemNode.add(account.getDbType().name());
            itemNode.add(account.getDbUri());
            itemNode.add(account.getSuperadminUserName());
            node.add(itemNode);
        }
        return node;
    }

    public Result accountTemplate(String key) {
        return ok(getTemplate(key,"/public/app/templates/account-template.json"));
    }

    private String getAccountsDefaultsJSON() {
        return getTemplate("settings_defaults", "/public/app/templates/account-template.json");
    }

    @Override
    public Result getDefaults() {
        return accountTemplate("settings_defaults");
    }
}
