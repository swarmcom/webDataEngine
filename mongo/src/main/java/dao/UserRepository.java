package dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import domain.MongoUser;

public interface UserRepository extends MongoRepository<MongoUser, String> {
    MongoUser findByUserName(String userName);
}
