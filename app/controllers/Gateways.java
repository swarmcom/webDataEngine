package controllers;

import api.domain.BeanDomain;
import api.domain.Gateway;
import api.service.MultiGatewayService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by mirceac on 6/2/16.
 */
@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_USER')")
public class Gateways extends BaseController {

    @Inject
    MultiGatewayService gatewayService;

    @Override
    protected BeanDomain addAbstract() throws Exception {
        Gateway gatewayToAdd = new Gateway();
        mergeDefaults(gatewayToAdd, getGatewayDefaultsJSON());
        merge(gatewayToAdd);
        return gatewayService.saveGateway(gatewayToAdd);
    }

    @Override
    protected BeanDomain getByNameAbstract(String name) throws Exception {
        return gatewayService.getGateway(name);
    }

    @Override
    protected BeanDomain getByIdAbstract(String id) throws Exception {
        return gatewayService.getGatewayById(id);
    }

    @Override
    protected Long deleteByNameAbstract(String name) throws Exception {
        return gatewayService.deleteGateway(name);
    }

    @Override
    protected Long deleteListAbstract() throws Exception {
        List<String> idsArray = convertIds();
        return gatewayService.deleteGateways(idsArray);
    }

    @Override
    protected BeanDomain modifyByNameAbstract(String name) throws Exception {
        Gateway existingGateway = gatewayService.getGateway(name);
        merge(existingGateway);
        return (existingGateway != null ? gatewayService.saveGateway(existingGateway) : null);
    }

    @Override
    protected BeanDomain modifyByIdAbstract(String id) throws Exception {
        Gateway existingGateway = gatewayService.getGatewayById(id);
        merge(existingGateway);
        return (existingGateway != null ? gatewayService.saveGateway(existingGateway) : null);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return gatewayService.getGateways(TokenUtil.getCurrentAccountId());
    }

    @Override
    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Gateway> gateways = gatewayService.getGateways();
        ArrayNode node = Json.newArray();
        for (Gateway gateway : gateways) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(gateway.getId());
            itemNode.add(gateway.getAccountId());
            itemNode.add(gateway.getName());
            itemNode.add(gateway.getAddress());
            node.add(itemNode);
        }
        return node;
    }

    public Result gatewayTemplate(String key) {
        return ok(getTemplate(key, "/public/app/templates/devices/gatewayTemplate.json"));
    }

    private String getGatewayDefaultsJSON() {
        return getTemplate("settings_defaults", "/public/app/templates/devices/gatewayTemplate.json");
    }

    @Override
    public Result getDefaults() {
        return gatewayTemplate("settings_defaults");
    }
}
