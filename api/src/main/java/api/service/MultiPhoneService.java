package api.service;

public interface MultiPhoneService extends PhoneService {
    public void createPhone(String serialNumber, String description, String firmwareVersion);
}
