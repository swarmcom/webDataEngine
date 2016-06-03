package tenancy.service;

import api.domain.Gateway;
import api.service.GatewayService;
import api.service.MultiGatewayService;
import api.service.MultiService;
import org.springframework.stereotype.Component;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Created by mirceac on 6/2/16.
 */

@Component
public class MultiTenantGatewayService implements MultiGatewayService {

    @Inject
    MultiService multiService;

    @Override
    public Gateway getGateway(String serialNumber) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getGateway(currentAccountId, serialNumber);
    }

    @Override
    public List<? extends Gateway> getGateways() {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getGateways(currentAccountId);
    }

    @Override
    public Gateway getGatewayById(String gatewayId) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getGatewayById(currentAccountId, gatewayId);
    }

    @Override
    public Long deleteGateway(String serialNumber) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return deleteGateway(currentAccountId, serialNumber);
    }

    @Override
    public Long deleteGateways(Collection<String> gatewayIds) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return deleteGateways(currentAccountId, gatewayIds);
    }

    @Override
    public Gateway getGateway(String accountId, String serialNumber) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.getGateway(accountId, serialNumber);
    }

    @Override
    public Gateway saveGateway(Gateway gateway) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        if (gateway.isNew()) {
            gateway.setAccountId(currentAccountId);
        }
        GatewayService gatewayService = multiService.getTenantGatewayService(currentAccountId);
        return gatewayService.saveGateway(gateway);
    }

    @Override
    public List<? extends Gateway> getGateways(String accountId) {
        GatewayService gatewayService = multiService.getTenantGatewayService(accountId);
        return gatewayService.getGateways(accountId);
    }

    @Override
    public Gateway getGatewayById(String accountId, String gatewayId) {
        GatewayService gatewayService = multiService.getCurrentTenantGatewayService();
        return gatewayService.getGatewayById(accountId, gatewayId);
    }

    @Override
    public Long deleteGateway(String accountId, String serialNumber) {
        GatewayService gatewayService = multiService.getCurrentTenantGatewayService();
        return gatewayService.deleteGateway(accountId, serialNumber);

    }

    @Override
    public Long deleteGateways(String accountId, Collection<String> gatewayIds) {
        GatewayService gatewayService = multiService.getCurrentTenantGatewayService();
        return gatewayService.deleteGateways(accountId, gatewayIds);
    }
}
