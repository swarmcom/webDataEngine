package controllers;

import api.domain.BeanDomain;
import api.domain.Phone;
import api.service.MultiPhoneService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Play;
import play.libs.Json;
import play.mvc.*;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;


@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Phones extends BaseController {

    @Inject
    MultiPhoneService phoneService;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Phone phoneToAdd = new Phone();
        mergeDefaults(phoneToAdd, getPolycomDefaultsJSON());
        merge(phoneToAdd);
        return phoneService.savePhone(phoneToAdd);
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return phoneService.getPhone(name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return phoneService.getPhoneById(id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return phoneService.deletePhone(name);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return phoneService.deletePhones(idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Phone existingPhone = phoneService.getPhone(name);
        merge(existingPhone);
        return (existingPhone != null ? phoneService.savePhone(existingPhone) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Phone existingPhone = phoneService.getPhoneById(id);
        merge(existingPhone);
        return (existingPhone != null ? phoneService.savePhone(existingPhone) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return phoneService.getPhones(TokenUtil.getCurrentAccountId());
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Phone> phones = phoneService.getPhones();
        ArrayNode node = Json.newArray();
        for (Phone phone : phones) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(phone.getId());
            itemNode.add(phone.getAccountId());
            itemNode.add(phone.getSerialNumber());
            itemNode.add(phone.getFirmwareVersion());
            itemNode.add(phone.getLines().size());
            node.add(itemNode);
        }
        return node;
    }

    public Result polycomTemplate(String key) {
        return ok(getTemplate(key, "/public/app/templates/devices/polycom/polycomTemplate.json"));
    }

    private String getPolycomDefaultsJSON() {
        return getTemplate("settings_defaults", "/public/app/templates/devices/polycom/polycomTemplate.json");
    }

    @Override
    public Result getDefaults() {
        return polycomTemplate("settings_defaults");
    }
}
