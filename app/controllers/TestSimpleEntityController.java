package controllers;

import api.domain.Account;
import api.domain.BeanDomain;
import api.service.AccountService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;
import play.mvc.BodyParser;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

@Component

public class TestSimpleEntityController extends SimpleEntityController {
    @Inject
    AccountService accountService;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Account accountToAdd = new Account();
        mergeDefaults(accountToAdd, getDefaultsJSON());
        merge(accountToAdd);
        Account savedAccount = accountService.saveAccount(accountToAdd);
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
        return getTemplate(key,"/public/app/templates/account-template.json");
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
        return null;
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
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
        return null;
    }
}

