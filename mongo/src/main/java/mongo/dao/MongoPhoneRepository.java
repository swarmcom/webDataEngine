package mongo.dao;

import api.domain.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface MongoPhoneRepository extends MongoRepository<Phone, String> {

    Phone findByAccountIdAndSerialNumber(String accountId, String serialNumber);

    List<Phone> findByAccountId(String accountId);

    List<Phone> findByAccountIdAndModel(String accountId, String model);

    Phone findByAccountIdAndId(String accountId, String id);

    Long deleteByAccountIdAndSerialNumber(String accountId, String serialNumber);

    Long deleteByAccountIdAndIdIn(String accountId, Collection<String> ids);
}
