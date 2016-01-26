package api.service;

import api.domain.Phone;

import java.util.List;

public interface PhoneService {
    Phone getPhone(String accountId, String serialNumber);

    void createPhone(String accountId, String serialNumber, String description, String firmwareVersion);

    public List<? extends Phone> getPhones(String accountId);
}
