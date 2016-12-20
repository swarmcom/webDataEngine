package mongo.dao;

import api.domain.Gateway;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by mirceac on 6/1/16.
 */
public interface MongoGatewayRepository extends MongoRepository<Gateway, String> {

    Gateway findByAccountIdAndSerialNumber(String accountId, String serialNumber);

    List<Gateway> findByAccountId(String accountId);

    List<Gateway> findByAccountIdAndModel(String accountId, String model);

    Gateway findByAccountIdAndId(String accountId, String id);

    Long deleteByAccountIdAndSerialNumber(String accountId, String serialNumber);

    Long deleteByAccountIdAndIdIn(String accountId, Collection<String> ids);

    Long deleteByAccountId(String accountId);
}
