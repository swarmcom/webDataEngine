package controllers;

import api.domain.Phone;
import api.service.MultiPhoneService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Play;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.io.IOException;


@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Phones extends Controller {

    @Inject
    MultiPhoneService phoneService;

    public Result polycomTemplate(String key) {
        return ok(getPolycomTemplate(key));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Phone phone = objectMapper.readValue(node.toString(), Phone.class);
            Phone phoneWithDefaults = objectMapper.readerForUpdating(phone).readValue(getPolycomDefaultsJSON());
            phoneService.savePhone(phoneWithDefaults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("created");
    }

    public Result get(String serialNumber) {
        Phone phone = phoneService.getPhone(serialNumber);
        if (phone != null) {
            JsonNode node = Json.toJson(phone);
            return ok(node.toString());
        } else {
            return ok("");
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modify(String serialNumber) {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String dataToUpdate = node.toString();
            Phone existingPhone = phoneService.getPhone(serialNumber);
            Phone phone = objectMapper.readValue(dataToUpdate, Phone.class);
            existingPhone.merge(phone);
            phoneService.savePhone(existingPhone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("created");
    }

    private String getPolycomTemplate(String key) {
        String schema = StringUtils.EMPTY;
        try {
            JsonNode node = new ObjectMapper().readTree(Play.application().getFile("/public/app/devices/polycom/polycomTemplate.json"));
            schema = node.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;
    }

    private String getPolycomDefaultsJSON() {
        return getPolycomTemplate("settings_defaults");
    }
}
