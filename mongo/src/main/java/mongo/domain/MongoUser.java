package mongo.domain;

import api.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class MongoUser implements User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private String password;

    private List<String> roles = new ArrayList<String>();


    public MongoUser() {
        userName = "Anonymous";
        password = "123";
    }
    public MongoUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        roles.add("ROLE_USER");
    }

    public MongoUser(String userName, String password, List<String> roles) {
        this.userName = userName;
        this.password = password;
        if (roles != null) {
            this.roles.addAll(roles);
        } else {
            this.roles.add("ROLE_USER");
        }
    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }
}
