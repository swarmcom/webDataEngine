package mongo.domain;

import api.domain.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class MongoRole implements Role {

    @Id
    private String id;

    @Indexed(unique = true)
    private String roleName;

    public MongoRole() {
        this.roleName = "ROLE_ADMIN";
    }

    public MongoRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
