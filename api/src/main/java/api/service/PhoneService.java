package api.service;

import api.domain.Phone;

import java.util.Collection;
import java.util.List;

public interface PhoneService {
    Phone getPhone(String accountId, String serialNumber);

    Phone createPhone(String accountId, String serialNumber, String description, String firmwareVersion);

    Phone savePhone(Phone phone);

    public List<? extends Phone> getPhones(String accountId);

    Phone getPhoneById (String accountId, String phoneId);

    Long deletePhone(String accountId, String serialNumber);

    Long deletePhones(String accountId, Collection<String> phoneIds);
}
