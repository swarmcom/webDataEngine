package tenancy.service;

import api.domain.Gateway;
import api.service.GatewayService;
import api.service.MultiService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Created by mirceac on 6/2/16.
 */

@Component
public class MultiTenantGatewayService implements GatewayService {

    @Inject
    MultiService multiService;

    @Override
    public Gateway getGateway(String accountId, String serialNumber) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.getGateway(accountId, serialNumber);
    }

    @Override
    public Gateway saveGateway(String accountId, Gateway gateway) {
        if (gateway.isNew()) {
            gateway.setAccountId(accountId);
        }
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.saveGateway(accountId, gateway);
    }

    @Override
    public List<? extends Gateway> getGateways(String accountId) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.getGateways(accountId);
    }

    @Override
    public List<? extends Gateway> getGateways(String accountId, String model) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.getGateways(accountId, model);
    }

    @Override
    public Gateway getGatewayById(String accountId, String gatewayId) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.getGatewayById(accountId, gatewayId);
    }

    @Override
    public Long deleteGateway(String accountId, String serialNumber) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.deleteGateway(accountId, serialNumber);

    }

    @Override
    public Long deleteGateways(String accountId, Collection<String> gatewayIds) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.deleteGateways(accountId, gatewayIds);
    }

    @Override
    public Long deleteGateways(String accountId) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.deleteGateways(accountId);
    }
}
