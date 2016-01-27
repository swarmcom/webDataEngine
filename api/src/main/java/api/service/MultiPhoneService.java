package api.service;

import api.domain.Phone;

public interface MultiPhoneService extends PhoneService {
    void createPhone(String serialNumber, String description, String firmwareVersion);
    Phone getPhone(String serialNumber);
}
