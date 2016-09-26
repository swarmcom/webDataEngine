package controllers;

import api.domain.BeanDomain;
import api.domain.Phone;
import api.service.MultiPhoneService;
import api.type.PhoneModel;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;


@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Phones extends ModeledEntityController {

    @Inject
    MultiPhoneService phoneService;

    @Override
    protected BeanDomain addAbstract(String model) throws Exception {
        Phone phoneToAdd = new Phone();
        mergeDefaults(phoneToAdd, getDefaultsJSON(model));
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
        List<? extends Phone> phones = phoneService.getPhonesByModel(model);
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
            itemNode.add(phone.getLines().size());
            node.add(itemNode);
        }
        return node;
    }
}
