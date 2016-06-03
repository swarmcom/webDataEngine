package api.service;

import api.domain.Gateway;
import api.domain.Phone;

import java.util.Collection;
import java.util.List;

/**
 * Created by mirceac on 6/1/16.
 */
public interface MultiGatewayService extends GatewayService {

    Gateway getGateway(String serialNumber);

    public List<? extends Gateway> getGateways();

    Gateway getGatewayById (String gatewayId);

    Long deleteGateway(String serialNumber);

    Long deleteGateways(Collection<String> gatewayIds);
}
