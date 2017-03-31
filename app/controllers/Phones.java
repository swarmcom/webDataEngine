package controllers;

import api.domain.BeanDomain;
import api.domain.Phone;
import api.service.PhoneService;
import api.type.PhoneModel;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "admin")
public class Phones extends ModeledEntityController {

    @Inject
    PhoneService phoneService;

    @Inject
    protected AppProfileManager appProfileManager;

    @Override
    protected BeanDomain addAbstract(String model) throws Exception {
        Phone phoneToAdd = new Phone();
        mergeDefaults(phoneToAdd, getDefaultsJSON(model));
        merge(phoneToAdd);
        return phoneService.savePhone(appProfileManager.getSessionAccountId(ctx()), phoneToAdd);
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return phoneService.getPhone(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return phoneService.getPhoneById(appProfileManager.getSessionAccountId(ctx()), id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return phoneService.deletePhone(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected Long deleteByAccountNameAbstract(String accountName) throws Exception {
        return phoneService.deletePhones(accountName);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return phoneService.deletePhones(appProfileManager.getSessionAccountId(ctx()), idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Phone existingPhone = phoneService.getPhone(appProfileManager.getSessionAccountId(ctx()), name);
        merge(existingPhone);
        return (existingPhone != null ? phoneService.savePhone(appProfileManager.getSessionAccountId(ctx()), existingPhone) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Phone existingPhone = phoneService.getPhoneById(appProfileManager.getSessionAccountId(ctx()), id);
        merge(existingPhone);
        return (existingPhone != null ? phoneService.savePhone(appProfileManager.getSessionAccountId(ctx()), existingPhone) : null);
    }

    protected String getDefaultsJSON(String model) {
        String result;
        switch (PhoneModel.valueOf(model)) {
            case PolycomVVX500:
                result = getTemplateJSON("settings_defaults", "/public/app/templates/devices/phone/polycomVVX500.json");
                break;
            case PolycomVVX300:
                result = getTemplateJSON("settings_defaults", "/public/app/templates/devices/phone/polycomVVX300.json");
                break;
            case PolycomVVX600:
                result = getTemplateJSON("settings_defaults", "/public/app/templates/devices/phone/polycomVVX600.json");
                break;
            case PolycomVVX400:
                result = getTemplateJSON("settings_defaults", "/public/app/templates/devices/phone/polycomVVX400.json");
                break;
            default:
                result = null;
        }
        return result;
    }

    @Override
    protected List<? extends BeanDomain> listAbstract(String model) throws Exception {
        return phoneService.getPhones(model);
    }

    @Override
    protected ArrayNode listArrayAbstract(String model) throws Exception {
        List<? extends Phone> phones = phoneService.getPhones(appProfileManager.getSessionAccountId(ctx()), model);
        return createPhoneArrayJson(phones);
    }

    @Override
    public Result getTemplateByModel(String key, String model) {
        Result result;
        switch (PhoneModel.valueOf(model)) {
            case PolycomVVX500:
                result = getTemplate(key, "/public/app/templates/devices/phone/polycomVVX500.json");
                break;
            case PolycomVVX300:
                result = getTemplate(key, "/public/app/templates/devices/phone/polycomVVX300.json");
                break;
            case PolycomVVX600:
                result = getTemplate(key, "/public/app/templates/devices/phone/polycomVVX600.json");
                break;
            case PolycomVVX400:
                result = getTemplate(key, "/public/app/templates/devices/phone/polycomVVX400.json");
                break;
            default:
                result = null;
        }

        return result;
    }

    protected ArrayNode createPhoneArrayJson(List<? extends Phone> phones) {
        ArrayNode node = Json.newArray();
        for (Phone phone : phones) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(phone.getId());
            itemNode.add(phone.getAccountId());
            itemNode.add(phone.getSerialNumber());
            itemNode.add(phone.getFirmwareVersion());
            List<String> lines = phone.getLines();
            itemNode.add(lines == null ? 0 : lines.size());
            node.add(itemNode);
        }
        return node;
    }
}
