package mongo.dao;

import api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoUserRepository extends MongoRepository<User, String> {
    User findByAccountIdAndUserName(String accountId, String userName);
    List<User> findByAccountId(String accountId);
}
