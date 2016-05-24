package tenancy.dao;

import api.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface DbAccountRepository extends MongoRepository<Account, String> {
    Account findByAccountName(String accountName);
    Account findById(String accountId);
    List<Account> findByIdIn(Collection<String> ids);
    Long deleteByAccountName(String accountName);
    Long deleteByIdIn(Collection<String> ids);
}
