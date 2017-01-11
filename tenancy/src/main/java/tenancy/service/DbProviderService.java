package tenancy.service;

import api.domain.Provider;
import api.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tenancy.dao.DbProviderRepository;

import java.util.Collection;
import java.util.List;

@Component
public class DbProviderService implements ProviderService {

    @Autowired
    DbProviderRepository providerRepository;

    @Override
    public Provider getProvider(String providerName) {
        return providerRepository.findByProviderName(providerName);
    }

    @Override
    public Provider getProviderById(String providerId) {
        return providerRepository.findById(providerId);
    }

    @Override
    public Provider createProvider(String providerName, String superadminUserName, String superadminPassword) {
        Provider provider = new Provider(providerName, superadminUserName, superadminPassword);
        return saveProvider(provider);
    }

    @Override
    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Long deleteProvider(String providerName) {
        return providerRepository.deleteByProviderName(providerName);
    }

    @Override
    public Long deleteProviders(Collection<String> providerIds) {
        return providerRepository.deleteByIdIn(providerIds);
    }

    @Override
    public List<? extends Provider> getProviders() {
        return providerRepository.findAll();
    }
}
