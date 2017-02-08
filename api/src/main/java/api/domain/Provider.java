package api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider extends BeanDomain<Provider> {

    protected String id;
    protected String providerName;
    protected String superadminUserName;
    protected String superadminPassword;
    protected Boolean digestEncoded;
    protected String description;
    protected String email;

    public Provider() {

    }

    public Provider(String providerName, String superadminUserName, String superadminPassword) {
        this.providerName = providerName;
        this.superadminUserName = superadminUserName;
        this.superadminPassword = superadminPassword;
    }

    public boolean isNew() {
        return StringUtils.isEmpty(this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getSuperadminUserName() {
        return superadminUserName;
    }

    public void setSuperadminUserName(String superadminUserName) {
        this.superadminUserName = superadminUserName;
    }

    public String getSuperadminPassword() {
        return superadminPassword;
    }

    public void setSuperadminPassword(String superadminPassword) {
        this.superadminPassword = superadminPassword;
    }

    public Boolean isDigestEncoded() {
        return digestEncoded;
    }

    public void setDigestEncoded(Boolean digestEncoded) {
        this.digestEncoded = digestEncoded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void merge(Provider provider) {
        String providerName = provider.getProviderName();
        String superadminUserName = provider.getSuperadminUserName();
        Boolean digestEncoded = provider.isDigestEncoded();
        String description = provider.getDescription();
        String email = provider.getEmail();

        if (providerName != null) {
            setProviderName(providerName);
        }

        if (superadminUserName != null) {
            setSuperadminUserName(superadminUserName);
        }

        if (digestEncoded != null) {
            setDigestEncoded(digestEncoded);
        }

        if (description != null) {
            setDescription(description);
        }

        if (email != null) {
            setEmail(email);
        }
    }
}
