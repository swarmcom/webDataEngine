package couchdb.domain;

import api.domain.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouchDBRole extends Role {

    private String revision;

    public CouchDBRole() {
        super();
    }

    public CouchDBRole(String accountId, String roleName) {
        super(accountId, roleName);
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
