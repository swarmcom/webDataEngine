package controllers;

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

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {

        Phone phoneToAdd = new Phone();
        try {
            mergeDefaults(phoneToAdd, getPolycomDefaultsJSON());
            merge(phoneToAdd);
            Phone savedPhone = phoneService.savePhone(phoneToAdd);
            return convert(savedPhone);
        } catch (Exception e) {
            return convert(null);
        }
    }

    public Result getById(String userId) {
        Phone phone = phoneService.getPhoneById(userId);
        return convert(phone);
    }

    public Result getBySerialNumber(String serialNumber) {
        Phone phone = phoneService.getPhone(serialNumber);
        return convert(phone);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyBySerialNumber(String serialNumber) {
        Phone existingPhone = phoneService.getPhone(serialNumber);
        try {
            merge(existingPhone);
            Phone modifiedPhone = (existingPhone != null ? modifiedPhone = phoneService.savePhone(existingPhone) : null);
            return convert(modifiedPhone);
        } catch (Exception ex) {
            return convert(null);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyById(String userId) {
        Phone existingPhone = phoneService.getPhoneById(userId);
        try {
            merge(existingPhone);
            Phone modifiedPhone = (existingPhone != null ? modifiedPhone = phoneService.savePhone(existingPhone) : null);
            return convert(modifiedPhone);
        } catch (Exception ex) {
            return convert(null);
        }
    }

    public Result list() {
        List<? extends Phone> phones = phoneService.getPhones(TokenUtil.getCurrentAccountId());
        JsonNode node = Json.toJson(phones);
        return ok(node.toString());
    }

    public Result delete(String serialNumber) {
        Long result = phoneService.deletePhone(serialNumber);
        return ok(String.valueOf(result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result deletePhones() {
        List<String> idsArray = convertIds();
        Long result = phoneService.deletePhones(idsArray);
        return ok(String.valueOf(result));
    }

    public Result listArray() {
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
        return ok(node.toString());
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
