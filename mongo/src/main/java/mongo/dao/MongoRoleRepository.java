package mongo.dao;

import mongo.domain.MongoRole;
import mongo.domain.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRoleRepository extends MongoRepository<MongoRole, String> {
    MongoRole findByRoleName(String userName);
}
