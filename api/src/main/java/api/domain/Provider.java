package api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider extends BeanDomain<Provider> {

    protected String id;
    protected String providerName;
    protected String superadminUserName;
    protected String superadminPassword;

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

    public void merge(Provider provider) {
        String providerName = provider.getProviderName();
        String superadminUserName = provider.getSuperadminUserName();
        String superadminPassword = provider.getSuperadminPassword();

        if (providerName != null) {
            setProviderName(providerName);
        }

        if (superadminUserName != null) {
            setSuperadminUserName(superadminUserName);
        }

        if (superadminPassword != null) {
            setSuperadminPassword(superadminPassword);
        }
    }
}
