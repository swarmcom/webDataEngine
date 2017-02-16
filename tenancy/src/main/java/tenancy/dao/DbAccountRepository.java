package tenancy.dao;

import api.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface DbAccountRepository extends MongoRepository<Account, String> {
    Account findByProviderNameAndAccountName(String providerName, String accountName);
    Account findByProviderNameAndId(String providerName, String accountId);
    List<Account> findByProviderName(String providerName);
    List<Account> findByAccountName(String accountName);
    List<Account> findByProviderNameAndIdIn(Collection<String> ids);
    Long deleteByProviderNameAndAccountName(String providerName, String accountName);
    Long deleteByProviderNameAndIdIn(String providerName, Collection<String> ids);
}
