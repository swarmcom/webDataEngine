package mongo.dao;

import api.domain.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoPhoneRepository extends MongoRepository<Phone, String> {
    Phone findByAccountIdAndSerialNumber(String accountId, String serialNumber);
    List<Phone> findByAccountId(String accountId);
}
