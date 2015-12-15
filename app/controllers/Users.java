package controllers;

import auth.BasicAuthentication;
import auth.OidcSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import api.domain.User;
import models.domain.ModelUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import api.service.UserService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Component
@BasicAuthentication
@Security.Authenticated(OidcSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Users extends Controller {
    @Inject
    UserService userService;

    public Result list() {
        List<? extends User> users = userService.getUsers();
        JsonNode node = Json.toJson(users);
        return ok(node.toString());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(node.toString(), ModelUser.class);
            userService.createUser(user.getUserName(), user.getPassword(), user.getRoles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("created");
    }
}
