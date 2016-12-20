package api.service;

import api.domain.Phone;

import java.util.Collection;
import java.util.List;

public interface PhoneService {
    Phone getPhone(String accountId, String serialNumber);

    Phone createPhone(String accountId, String serialNumber, String description, String firmwareVersion);

    Phone savePhone(String accountId, Phone phone);

    public List<? extends Phone> getPhones(String accountId);

    public List<? extends Phone> getPhones(String accountId, String model);

    Phone getPhoneById (String accountId, String phoneId);

    Long deletePhone(String accountId, String serialNumber);

    Long deletePhones(String accountId, Collection<String> phoneIds);

    Long deletePhones(String accountId);
}
