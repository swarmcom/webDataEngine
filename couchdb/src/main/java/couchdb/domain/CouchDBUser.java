package couchdb.domain;

import api.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouchDBUser extends User {

    private String revision;

    public CouchDBUser() {
        super();
    }

    public CouchDBUser(String userName, String password) {
        super(userName, password);
    }

    public CouchDBUser(String userName, String password, List<String> roles) {
        super(userName, password, roles);
    }

    @JsonProperty("_id")
    public String getId() {
        return super.getId();
    }

    @JsonProperty("_id")
    public void setId(String id) {
        super.setId(id);
    }

    @JsonProperty("_rev")
    public String getRevision() {
        return revision;
    }

    @JsonProperty("_rev")
    public void setRevision(String revision) {
        this.revision = revision;
    }
}
