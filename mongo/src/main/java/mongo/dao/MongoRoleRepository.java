package mongo.dao;

import api.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoRoleRepository extends MongoRepository<Role, String> {
    Role findByAccountIdAndRoleName(String accountId, String roleName);
    List<Role> findByAccountId(String accountId);
}
