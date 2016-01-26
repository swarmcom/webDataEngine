package controllers;

import api.domain.Phone;
import api.domain.User;
import api.service.MultiPhoneService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Logger;
import play.Play;
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
        String schema = StringUtils.EMPTY;

        try {
            JsonNode node = new ObjectMapper().readTree(Play.application().getFile("/public/app/devices/polycom/polycomTemplate.json"));
            schema = node.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok(schema);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        Logger.info("MIRCEA add phone executed");
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Phone phone = objectMapper.readValue(node.toString(), Phone.class);
            Logger.info("MIRCEAAA " + phone);
            phoneService.createPhone(phone.getSerialNumber(), phone.getDescription(), phone.getFirmwareVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("created");
    }
}
