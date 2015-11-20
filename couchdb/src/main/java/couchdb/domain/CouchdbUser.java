package couchdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import domain.User;
import org.ektorp.support.CouchDbDocument;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouchdbUser extends CouchDbDocument implements User {

    private String userName;

    private String password;

    public CouchdbUser() {
        userName = "Anonymous";
        password = "123";
    }
    public CouchdbUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
