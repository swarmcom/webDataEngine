package mongo.service;

import api.domain.Gateway;
import api.service.GatewayService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.dao.MongoGatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * Created by mirceac on 6/1/16.
 */
@Component
public class MongoGatewayService implements GatewayService {

    @Autowired
    MongoGatewayRepository gatewayRepository;

    @Autowired
    DBCollection gatewayCollection;

    @PostConstruct
    public void init() {
        gatewayCollection.createIndex(new BasicDBObject("serialNumber", 1), new BasicDBObject("unique", true));
    }

    @Override
    public Gateway getGateway(String accountId, String serialNumber) {
        return gatewayRepository.findByAccountIdAndSerialNumber(accountId, serialNumber);
    }

    @Override
    public Gateway saveGateway(Gateway gateway) {
        return gatewayRepository.save(gateway);
    }

    @Override
    public List<? extends Gateway> getGateways(String accountId) {
        return gatewayRepository.findByAccountId(accountId);
    }

    @Override
    public Gateway getGatewayById(String accountId, String gatewayId) {
        return gatewayRepository.findByAccountIdAndId(accountId, gatewayId);
    }

    @Override
    public Long deleteGateway(String accountId, String serialNumber) {
        return gatewayRepository.deleteByAccountIdAndSerialNumber(accountId, serialNumber);
    }

    @Override
    public Long deleteGateways(String accountId, Collection<String> gatewayIds) {
        return gatewayRepository.deleteByAccountIdAndIdIn(accountId, gatewayIds);
    }
}
