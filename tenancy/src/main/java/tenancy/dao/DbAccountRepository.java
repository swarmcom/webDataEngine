package tenancy.dao;

import api.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DbAccountRepository extends MongoRepository<Account, String> {
    Account findByAccountName(String accountName);
}
