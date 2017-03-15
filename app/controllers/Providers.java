package controllers;


import api.domain.Account;
import api.domain.BeanDomain;
import api.domain.Provider;
import api.service.ProviderService;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.binary.StringUtils;
import org.pac4j.play.java.Secure;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.With;
import security.encoder.SecurityPasswordEncoder;
import security.util.EncoderUtil;

import javax.inject.Inject;
import java.util.List;

@With(SessionAuthenticatedAction.class)
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "superadmin")
public class Providers extends SimpleEntityController {

    @Inject
    ProviderService providerService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Provider providerToAdd = new Provider();
        mergeDefaults(providerToAdd, getDefaultsJSON());
        merge(providerToAdd);
        if (providerToAdd.isDigestEncoded()) {
            providerToAdd.setSuperadminPassword(EncoderUtil.digestEncodePassword(providerToAdd.getSuperadminUserName(), EncoderUtil.DIGEST_REALM, providerToAdd.getSuperadminPassword()));
        } else {
            providerToAdd.setSuperadminPassword(passwordEncoder.encode(providerToAdd.getSuperadminPassword()));
        }
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
            itemNode.add(provider.getDescription());
            itemNode.add(provider.getEmail());
            itemNode.add(provider.isSuspended());
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
        return getTemplateJSON("settings_defaults", "/public/app/templates/provider-template.json");
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
        Provider providerToMerge = (Provider)merge(existingProvider);
        String superadminPassword = providerToMerge.getSuperadminPassword();
        if (superadminPassword!= null && !StringUtils.equals(existingProvider.getSuperadminPassword(), superadminPassword)) {
            if (existingProvider.isDigestEncoded()) {
                existingProvider.setSuperadminPassword(EncoderUtil.digestEncodePassword(existingProvider.getSuperadminUserName(), EncoderUtil.DIGEST_REALM, superadminPassword));
            } else {
                existingProvider.setSuperadminPassword(passwordEncoder.encode(superadminPassword));
            }
        }
        return (existingProvider != null ? providerService.saveProvider(existingProvider) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Provider existingProvider = providerService.getProviderById(id);
        Provider providerToMerge = (Provider)merge(existingProvider);
        String superadminPassword = providerToMerge.getSuperadminPassword();
        if (superadminPassword!= null && !StringUtils.equals(existingProvider.getSuperadminPassword(), superadminPassword)) {
            if (existingProvider.isDigestEncoded()) {
                existingProvider.setSuperadminPassword(EncoderUtil.digestEncodePassword(existingProvider.getSuperadminUserName(), EncoderUtil.DIGEST_REALM, superadminPassword));
            } else {
                existingProvider.setSuperadminPassword(passwordEncoder.encode(superadminPassword));
            }
        }
        return (existingProvider != null ? providerService.saveProvider(existingProvider) : null);
    }
}
