package controllers;

import api.domain.BeanDomain;
import api.domain.User;
import api.service.MultiUserService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.DigestAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;
import security.encoder.SecurityPasswordEncoder;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.List;

@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@DigestAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Users extends SimpleEntityController {
    @Inject
    MultiUserService userService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        User userToAdd = new User();
        mergeDefaults(userToAdd, getUserDefaultsJSON());
        merge(userToAdd);
        userToAdd.setPassword(userToAdd.getPassword());
        return userService.saveUser(userToAdd);
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return userService.getUser(name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return userService.getUserById(id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return userService.deleteUser(name);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return userService.deleteUsers(idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        User existingUser = userService.getUser(name);
        merge(existingUser);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        return (existingUser != null ? userService.saveUser(existingUser) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        User existingUser = userService.getUserById(id);
        merge(existingUser);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        return (existingUser != null ? userService.saveUser(existingUser) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return userService.getUsers(TokenUtil.getCurrentAccountId());
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
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
        return node;
    }

    public Result getTemplate(String key) {
        return getTemplate(key,"/public/app/templates/user-template.json");
    }

    @Override
    protected String getDefaultsJSON() {
        return getTemplateJSON("settings_defaults", "/public/app/templates/user-template.json");
    }

    private String getUserDefaultsJSON() {
        return getTemplate("settings_defaults", "/public/app/templates/user-template.json");
    }

    @Override
    public Result getDefaults() {
        return userTemplate("settings_defaults");
    }
}
