package mongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import mongo.domain.MongoUser;

public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
    MongoUser findByUserName(String userName);
}
