package controllers;

import api.domain.Account;
import api.domain.BeanDomain;
import api.service.AccountService;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import managers.AppProfileManager;
import org.pac4j.play.java.Secure;
import org.springframework.stereotype.Component;

import play.Logger;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;

@Component

@With(SessionAuthenticatedAction.class)
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "superadmin")
public class Accounts extends SimpleEntityController {

    @Inject
    AccountService accountService;

    @Inject
    protected AppProfileManager appProfileManager;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Account accountToAdd = new Account();
        mergeDefaults(accountToAdd, getDefaultsJSON());
        merge(accountToAdd);
        Account savedAccount = accountService.saveAccount(appProfileManager.getSessionProviderId(ctx()), accountToAdd);
        return savedAccount;
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return accountService.getAccount(appProfileManager.getSessionProviderId(ctx()), name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return accountService.getAccountById(appProfileManager.getSessionProviderId(ctx()), id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return accountService.deleteAccount(appProfileManager.getSessionProviderId(ctx()), name);
    }

    @Override
    protected Long deleteByAccountNameAbstract(String accountName) throws Exception {
        return deleteByNameAbstract(accountName);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return accountService.deleteAccounts(appProfileManager.getSessionProviderId(ctx()), idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Account existingAccount = accountService.getAccount(appProfileManager.getSessionProviderId(ctx()), name);
        merge(existingAccount);
        return (existingAccount != null ? accountService.saveAccount(appProfileManager.getSessionProviderId(ctx()), existingAccount) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Account existingAccount = accountService.getAccountById(appProfileManager.getSessionProviderId(ctx()), id);
        merge(existingAccount);
        return (existingAccount != null ? accountService.saveAccount(appProfileManager.getSessionProviderId(ctx()), existingAccount) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return accountService.getAccounts(appProfileManager.getSessionProviderId(ctx()));
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Account> accounts = accountService.getAccounts(appProfileManager.getSessionProviderId(ctx()));
        ArrayNode node = Json.newArray();
        for (Account account : accounts) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(account.getId());
            itemNode.add(account.getAccountName());
            itemNode.add(account.getCompanyName());
            itemNode.add(account.getDescription());
            itemNode.add(account.getEmail());
            itemNode.add(account.isSuspended());
            node.add(itemNode);
        }
        return node;
    }

    public Result getTemplate(String key) {
        return getTemplate(key,"/public/app/templates/account-template.json");
    }

    protected String getDefaultsJSON() {
        return getTemplateJSON("settings_defaults", "/public/app/templates/account-template.json");
    }
}
