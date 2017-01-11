package controllers;


import api.domain.Account;
import api.domain.BeanDomain;
import api.domain.Provider;
import api.service.ProviderService;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.pac4j.play.java.Secure;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.With;

import javax.inject.Inject;
import java.util.List;

@With(SessionAuthenticatedAction.class)
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "superadmin")
public class Providers extends SimpleEntityController {

    @Inject
    ProviderService providerService;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Provider providerToAdd = new Provider();
        mergeDefaults(providerToAdd, getDefaultsJSON());
        merge(providerToAdd);
        Provider savedProvider = providerService.saveProvider(providerToAdd);
        return savedProvider;
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return providerService.getProviders();
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Provider> providers = providerService.getProviders();
        ArrayNode node = Json.newArray();
        for (Provider provider : providers) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(provider.getId());
            itemNode.add(provider.getProviderName());
            itemNode.add(provider.getSuperadminUserName());
            node.add(itemNode);
        }
        return node;
    }

    @Override
    public Result getTemplate(String key) {
        return getTemplate(key,"/public/app/templates/provider-template.json");
    }

    @Override
    protected String getDefaultsJSON() {
        return getTemplateJSON("settings_defaults", "/public/app/templates/account-template.json");
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return providerService.getProvider(name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return providerService.getProviderById(id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return providerService.deleteProvider(name);
    }

    @Override
    protected Long deleteByAccountNameAbstract(String accountName) throws Exception {
        return null;
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return providerService.deleteProviders(idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Provider existingProvider = providerService.getProvider(name);
        merge(existingProvider);
        return (existingProvider != null ? providerService.saveProvider(existingProvider) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Provider existingProvider = providerService.getProviderById(id);
        merge(existingProvider);
        return (existingProvider != null ? providerService.saveProvider(existingProvider) : null);
    }
}
