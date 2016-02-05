package tenancy.service;

import api.domain.Phone;
import api.service.MultiPhoneService;
import api.service.MultiService;
import api.service.PhoneService;
import org.springframework.stereotype.Component;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.List;

@Component
public class MultiTenantPhoneService implements MultiPhoneService {

    @Inject
    MultiService multiService;

    @Override
    public Phone getPhone(String accountId, String serialNumber) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhone(accountId, serialNumber);
    }

    @Override
    public void createPhone(String accountId, String serialNumber, String description, String firmwareVersion) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        phoneService.createPhone(accountId, serialNumber, description,firmwareVersion);
    }

    @Override
    public void savePhone(Phone phone) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        if (phone.isNew()) {
            phone.setAccountId(currentAccountId);
        }
        PhoneService phoneService = multiService.getTenantPhoneService(currentAccountId);
        phoneService.savePhone(phone);
    }

    @Override
    public List<? extends Phone> getPhones(String accountId) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhones(accountId);
    }

    @Override
    public void createPhone(String serialNumber, String description, String firmwareVersion) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        createPhone(currentAccountId, serialNumber, description, firmwareVersion);
    }

    @Override
    public Phone getPhone(String serialNumber) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getPhone(currentAccountId, serialNumber);
    }
}
