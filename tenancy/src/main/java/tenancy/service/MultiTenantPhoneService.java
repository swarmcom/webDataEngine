package tenancy.service;

import api.domain.Phone;
import api.service.MultiPhoneService;
import api.service.MultiService;
import api.service.PhoneService;
import org.springframework.stereotype.Component;
import security.util.TokenUtil;

import javax.inject.Inject;
import java.util.Collection;
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
    public Phone createPhone(String accountId, String serialNumber, String description, String firmwareVersion) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.createPhone(accountId, serialNumber, description,firmwareVersion);
    }

    @Override
    public Phone savePhone(Phone phone) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        if (phone.isNew()) {
            phone.setAccountId(currentAccountId);
        }
        PhoneService phoneService = multiService.getTenantPhoneService(currentAccountId);
        return phoneService.savePhone(phone);
    }

    @Override
    public List<? extends Phone> getPhones(String accountId) {
        PhoneService phoneService = multiService.getTenantPhoneService(accountId);
        return phoneService.getPhones(accountId);
    }

    @Override
    public Phone getPhoneById(String accountId, String phoneId) {
        PhoneService phoneService = multiService.getCurrentTenantPhoneService();
        return phoneService.getPhoneById(accountId, phoneId);
    }

    @Override
    public Long deletePhone(String accountId, String serialNumber) {
        PhoneService phoneService = multiService.getCurrentTenantPhoneService();
        return phoneService.deletePhone(accountId, serialNumber);
    }

    @Override
    public Long deletePhones(String accountId, Collection<String> phoneIds) {
        PhoneService phoneService = multiService.getCurrentTenantPhoneService();
        return phoneService.deletePhones(accountId, phoneIds);
    }

    @Override
    public Phone createPhone(String serialNumber, String description, String firmwareVersion) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return createPhone(currentAccountId, serialNumber, description, firmwareVersion);
    }

    @Override
    public Phone getPhone(String serialNumber) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getPhone(currentAccountId, serialNumber);
    }

    @Override
    public List<? extends Phone> getPhones() {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getPhones(currentAccountId);
    }

    @Override
    public Phone getPhoneById(String phoneId) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return getPhoneById(currentAccountId, phoneId);
    }

    @Override
    public Long deletePhone(String serialNumber) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return deletePhone(currentAccountId, serialNumber);
    }

    @Override
    public Long deletePhones(Collection<String> phoneIds) {
        String currentAccountId = TokenUtil.getCurrentAccountId();
        return deletePhones(currentAccountId, phoneIds);
    }
}
