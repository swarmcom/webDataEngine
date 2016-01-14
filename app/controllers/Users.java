package controllers;

import api.domain.User;
import api.service.MultiUserService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Users extends Controller {
    @Inject
    MultiUserService userService;

    @Inject
    private PasswordEncoder passwordEncoder;

    public Result list() {
        List<? extends User> users = userService.getUsers(TokenUtil.getCurrentAccountId());
        JsonNode node = Json.toJson(users);
        return ok(node.toString());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(node.toString(), User.class);
            userService.createUser(user.getUserName(), passwordEncoder.encode(user.getPassword()), user.getRoles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("created");
    }
}
