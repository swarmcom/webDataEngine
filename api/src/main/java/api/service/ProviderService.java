package api.service;


import api.domain.Provider;

import java.util.Collection;
import java.util.List;

public interface ProviderService {

    Provider getProvider (String providerName);

    Provider getProviderById (String providerId);

    Provider createProvider (String providerName, String superadminUserName, String superadminPassword);

    Provider saveProvider(Provider provider);

    Long deleteProvider(String providerName);

    Long deleteProviders(Collection<String> providerIds);

    List<? extends Provider> getProviders();
}
