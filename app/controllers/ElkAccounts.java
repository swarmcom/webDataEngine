package controllers;

import api.domain.Account;
import api.domain.BeanDomain;
import api.service.AccountService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Inject;
import play.mvc.Result;

import java.util.List;

/**
 * Created by mirceac on 4/14/17.
 */
public class ElkAccounts extends SimpleEntityController {

    @Inject
    AccountService accountService;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Account accountToAdd = new Account();
        mergeDefaults(accountToAdd, getDefaultsJSON());
        merge(accountToAdd);
        Account savedAccount = accountService.saveAccount("Subscription", accountToAdd);
        return savedAccount;
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return null;
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        return null;
    }

    @Override
    public Result getTemplate(String key) {
        return null;
    }

    @Override
    protected String getDefaultsJSON() {
        return getTemplateJSON("settings_defaults", "/public/app/templates/account-template.json");
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return null;
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return accountService.getAccountById("Subscription", id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return null;
    }

    @Override
    protected Long deleteByAccountNameAbstract(String accountName) throws Exception {
        return null;
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        return null;
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        return null;
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Account existingAccount = accountService.getAccountById("Subscription", id);
        merge(existingAccount);
        return (existingAccount != null ? accountService.saveAccount("Subscription", existingAccount) : null);
    }
}
