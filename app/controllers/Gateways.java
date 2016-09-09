package controllers;

import api.domain.BeanDomain;
import api.domain.Gateway;
import api.service.MultiGatewayService;
import api.type.GatewayModel;
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
public class Gateways extends ModeledEntityController {

    @Inject
    MultiGatewayService gatewayService;

    protected BeanDomain addAbstract(String model) throws Exception {
        Gateway gatewayToAdd = new Gateway();
        mergeDefaults(gatewayToAdd, getDefaultsJSON(model));
        merge(gatewayToAdd);
        return gatewayService.saveGateway(gatewayToAdd);
    }

    @Override
    protected List<? extends BeanDomain> listAbstract(String model) throws Exception {
        return gatewayService.getGatewaysByModel(model);
    }

    @Override
    protected ArrayNode listArrayAbstract(String model) throws Exception {
        List<? extends Gateway> gateways = gatewayService.getGatewaysByModel(model);
        return createGatewayArrayJson(gateways);
    }

    @Override
    public Result getTemplateByModel(String key, String model) {
        Result result;
        switch (GatewayModel.valueOf(model)) {
            case AudioCodesMediant1000:
                result = getTemplate(key, "/public/app/templates/devices/gateway/audioCodesMediant1000.json");
                break;
            default:
                result = null;
        }

        return result;
    }

    protected String getDefaultsJSON(String model) {
        String result;
        switch (GatewayModel.valueOf(model)) {
            case AudioCodesMediant1000:
                result = getTemplateJSON("settings_defaults", "/public/app/templates/devices/gateway/audioCodesMediant1000.json");
                break;
            default:
                result = null;
        }
        return result;
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

    protected List<? extends BeanDomain> listAbstract() throws Exception {
        return gatewayService.getGateways();
    }

    protected ArrayNode listArrayAbstract() throws Exception {
        List<? extends Gateway> gateways = gatewayService.getGateways();
        return createGatewayArrayJson(gateways);
    }

    protected ArrayNode createGatewayArrayJson(List<? extends Gateway> gateways) {
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

}
