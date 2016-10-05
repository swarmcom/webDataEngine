package api.service;

import api.domain.Gateway;

import java.util.Collection;
import java.util.List;

/**
 * Created by mirceac on 6/1/16.
 */
public interface GatewayService {

    Gateway getGateway(String accountId, String serialNumber);

    Gateway saveGateway(String accountId, Gateway gateway);

    public List<? extends Gateway> getGateways(String accountId);

    public List<? extends Gateway> getGateways(String accountId, String model);

    Gateway getGatewayById (String accountId, String gatewayId);

    Long deleteGateway(String accountId, String serialNumber);

    Long deleteGateways(String accountId, Collection<String> gatewayIds);
}
