package mongo.service;

import api.domain.Phone;
import api.service.PhoneService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.dao.MongoPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Component
public class MongoPhoneService implements PhoneService {

    @Autowired
    MongoPhoneRepository phoneRepository;

    @Autowired
    DBCollection phoneCollection;

    @PostConstruct
    public void init() {
        phoneCollection.createIndex(new BasicDBObject("serialNumber", 1), new BasicDBObject("unique", true));
    }

    @Override
    public Phone getPhone(String accountId, String serialNumber) {
        return phoneRepository.findByAccountIdAndSerialNumber(accountId, serialNumber);
    }

    @Override
    public Phone createPhone(String accountId, String serialNumber, String description, String firmwareVersion) {
        return phoneRepository.save(new Phone(accountId, serialNumber, description, firmwareVersion));
    }

    @Override
    public Phone savePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public List<? extends Phone> getPhones(String accountId) {
        return phoneRepository.findByAccountId(accountId);
    }

    @Override
    public Phone getPhoneById(String accountId, String phoneId) {
        return phoneRepository.findByAccountIdAndId(accountId, phoneId);
    }

    @Override
    public Long deletePhone(String accountId, String serialNumber) {
        return phoneRepository.deleteByAccountIdAndSerialNumber(accountId, serialNumber);
    }

    @Override
    public Long deletePhones(String accountId, Collection<String> phoneIds) {
        return phoneRepository.deleteByAccountIdAndIdIn(accountId, phoneIds);
    }
}
