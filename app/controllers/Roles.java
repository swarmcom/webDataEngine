package controllers;

import api.domain.BeanDomain;
import api.domain.Role;
import api.service.RoleService;
import auth.SessionAuthenticatedAction;
import com.fasterxml.jackson.databind.node.ArrayNode;
import managers.AppProfileManager;
import org.pac4j.play.java.Secure;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;
import security.encoder.SecurityPasswordEncoder;

import javax.inject.Inject;
import java.util.List;

@Component
@With(SessionAuthenticatedAction.class)
@Secure(clients = "DirectBasicAuthClient, DirectDigestAuthClient", authorizers = "admin")
public class Roles extends SimpleEntityController {
    @Inject
    RoleService roleService;

    @Inject
    private SecurityPasswordEncoder passwordEncoder;

    @Inject
    protected AppProfileManager appProfileManager;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Role roleToAdd = new Role();
        mergeDefaults(roleToAdd, getDefaultsJSON());
        merge(roleToAdd);
        return roleService.saveRole(appProfileManager.getSessionAccountId(ctx()), roleToAdd);
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return roleService.getRole(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return roleService.getRoleById(appProfileManager.getSessionAccountId(ctx()), id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return roleService.deleteRole(appProfileManager.getSessionAccountId(ctx()), name);
    }

    @Override
    protected Long deleteByAccountNameAbstract(String accountName) throws Exception {
        return roleService.deleteRoles(accountName);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return roleService.deleteRoles(appProfileManager.getSessionAccountId(ctx()), idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Role existingRole = roleService.getRole(appProfileManager.getSessionAccountId(ctx()), name);
        merge(existingRole);
        return (existingRole != null ? roleService.saveRole(appProfileManager.getSessionAccountId(ctx()), existingRole) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Role existingRole = roleService.getRoleById(appProfileManager.getSessionAccountId(ctx()), id);
        merge(existingRole);
        return (existingRole != null ? roleService.saveRole(appProfileManager.getSessionAccountId(ctx()), existingRole) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return roleService.getRoles(appProfileManager.getSessionAccountId(ctx()));
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Role> roles = roleService.getRoles(appProfileManager.getSessionAccountId(ctx()));
        ArrayNode node = Json.newArray();
        for (Role role : roles) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(role.getId());
            itemNode.add(role.getAccountId());
            itemNode.add(role.getRoleName());

            node.add(itemNode);
        }
        return node;
    }

    public Result getTemplate(String key) {
        return null;
    }

    @Override
    protected String getDefaultsJSON() {
        return null;
    }
}
