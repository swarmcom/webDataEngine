package tenancy.dao;

import api.domain.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface DbProviderRepository extends MongoRepository<Provider, String> {
    Provider findByProviderName(String providerName);
    Provider findById(String providerId);
    List<Provider> findByIdIn(Collection<String> ids);
    Long deleteByProviderName(String providerName);
    Long deleteByIdIn(Collection<String> ids);
}
