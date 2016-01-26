package mongo.dao;

import api.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRoleRepository extends MongoRepository<Role, String> {
    Role findByRoleName(String roleName);
}
