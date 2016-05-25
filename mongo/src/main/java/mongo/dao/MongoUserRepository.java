package mongo.dao;

import api.domain.Account;
import api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface MongoUserRepository extends MongoRepository<User, String> {
    User findByAccountIdAndUserName(String accountId, String userName);
    List<User> findByAccountId(String accountId);
    User findByAccountIdAndId(String accountId, String id);
    Long deleteByAccountIdAndUserName(String accountId, String accountName);
    Long deleteByAccountIdAndIdIn(String accountId, Collection<String> ids);
}
