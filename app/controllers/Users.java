package controllers;

import api.domain.User;
import api.service.MultiUserService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.DigestAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import security.encoder.SecurityPasswordEncoder;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@DigestAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Users extends BaseController {
    @Inject
    MultiUserService userService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

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
        User savedUser = null;
        try {
            User user = objectMapper.readValue(node.toString(), User.class);
            savedUser = userService.createUser(user.getUserName(), passwordEncoder.encode(user.getPassword()), user.getRoles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convert(savedUser);
    }

    public Result getByName(String userName) {
        User user = userService.getUser(userName);
        return convert(user);
    }

    public Result getById(String userId) {
        User user = userService.getUserById(userId);
        return convert(user);
    }

    public Result delete(String userName) {
        Long result = userService.deleteUser(userName);
        return ok(String.valueOf(result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteUsers() {
        List<String> idsArray = convertIds();
        Long result = userService.deleteUsers(idsArray);
        return ok(String.valueOf(result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyByName(String userName) {
        User existingUser = userService.getUser(userName);
        try {
            merge(existingUser);
            existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
            User modifiedUser = (existingUser != null ? modifiedUser = userService.saveUser(existingUser) : null);
            return convert(modifiedUser);
        } catch(Exception ex) {
            return convert(null);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyById(String userId) {
        User existingUser = userService.getUserById(userId);
        try {
            merge(existingUser);
            existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
            User modifiedUser = (existingUser != null ? modifiedUser = userService.saveUser(existingUser) : null);
            return convert(modifiedUser);
        } catch (Exception ex) {
            return convert(null);
        }
    }

    public Result listArray() {
        List<? extends User> users = userService.getUsers();
        ArrayNode node = Json.newArray();
        for (User user : users) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(user.getId());
            itemNode.add(user.getAccountId());
            itemNode.add(user.getUserName());
            itemNode.add(StringUtils.join(user.getRoles()));
            node.add(itemNode);
        }
        return ok(node.toString());
    }

    public Result userTemplate(String key) {
        return ok(getTemplate(key,"/public/app/templates/user-template.json"));
    }
}
