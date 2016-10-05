package tenancy.service;

import api.domain.Phone;
import api.service.MultiService;
import api.service.PhoneService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@Component
public class MultiTenantPhoneService implements PhoneService {

    @Inject
    MultiService multiService;

    @Override
    public Phone getPhone(String accountId, String serialNumber) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhone(accountId, serialNumber);
    }

    @Override
    public Phone createPhone(String accountId, String serialNumber, String description, String firmwareVersion) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.createPhone(accountId, serialNumber, description,firmwareVersion);
    }

    @Override
    public Phone savePhone(String accountId, Phone phone) {
        if (phone.isNew()) {
            phone.setAccountId(accountId);
        }
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.savePhone(accountId, phone);
    }

    @Override
    public List<? extends Phone> getPhones(String accountId) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhones(accountId);
    }

    @Override
    public List<? extends Phone> getPhones(String accountId, String model) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhones(accountId, model);
    }

    @Override
    public Phone getPhoneById(String accountId, String phoneId) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhoneById(accountId, phoneId);
    }

    @Override
    public Long deletePhone(String accountId, String serialNumber) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.deletePhone(accountId, serialNumber);
    }

    @Override
    public Long deletePhones(String accountId, Collection<String> phoneIds) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.deletePhones(accountId, phoneIds);
    }
}
