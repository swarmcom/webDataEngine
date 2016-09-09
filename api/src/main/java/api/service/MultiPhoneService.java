package api.service;

import api.domain.Phone;

import java.util.Collection;
import java.util.List;

public interface MultiPhoneService extends PhoneService {

    Phone createPhone(String serialNumber, String description, String firmwareVersion);

    Phone getPhone(String serialNumber);

    List<? extends Phone> getPhones();

    List<? extends Phone> getPhonesByModel(String model);

    Phone getPhoneById (String phoneId);

    Long deletePhone(String serialNumber);

    Long deletePhones(Collection<String> phoneIds);
}
