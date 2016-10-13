package controllers;

import api.domain.BeanDomain;
import api.domain.User;
import api.service.UserService;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.databind.node.ArrayNode;
import managers.AppProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.pac4j.play.java.Secure;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;
import security.encoder.SecurityPasswordEncoder;
import security.util.EncoderUtil;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
@With(SessionAuthenticatedAction.class)
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "admin")
public class Users extends SimpleEntityController {
    @Inject
    UserService userService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    @Inject
    protected AppProfileManager appProfileManager;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        User userToAdd = new User();
        mergeDefaults(userToAdd, getDefaultsJSON());
        merge(userToAdd);
        if (userToAdd.isDigestEncoded()) {
            userToAdd.setPassword(EncoderUtil.digestEncodePassword(userToAdd.getUserName(), EncoderUtil.DIGEST_REALM, userToAdd.getPassword()));
        } else {
            userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));
        }
        return userService.saveUser(appProfileManager.getSessionAccountId(ctx()), userToAdd);
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return userService.getUser(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return userService.getUserById(appProfileManager.getSessionAccountId(ctx()), id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return userService.deleteUser(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return userService.deleteUsers(appProfileManager.getSessionAccountId(ctx()), idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        User existingUser = userService.getUser(appProfileManager.getSessionAccountId(ctx()), name);
        merge(existingUser);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        return (existingUser != null ? userService.saveUser(appProfileManager.getSessionAccountId(ctx()), existingUser) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        User existingUser = userService.getUserById(appProfileManager.getSessionAccountId(ctx()), id);
        merge(existingUser);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        return (existingUser != null ? userService.saveUser(appProfileManager.getSessionAccountId(ctx()), existingUser) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return userService.getUsers(appProfileManager.getSessionAccountId(ctx()));
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends User> users = userService.getUsers(appProfileManager.getSessionAccountId(ctx()));
        ArrayNode node = Json.newArray();
        for (User user : users) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(user.getId());
            itemNode.add(user.getAccountId());
            itemNode.add(user.getUserName());
            itemNode.add(StringUtils.join(user.getRoles()));
            Date birthDate = user.getBirthDate();
            itemNode.add(birthDate != null ? DateFormatUtils.format(birthDate, DATE_FORMAT_1) : "");
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
}
