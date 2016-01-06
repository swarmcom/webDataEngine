package mongo.dao;

import api.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<User, String> {
    User findByUserName(String userName);
}
