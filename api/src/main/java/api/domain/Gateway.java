package api.domain;

import api.type.GatewayModel;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mirceac on 6/1/16.
 */
public class Gateway extends BeanDomain<Gateway> {
    protected String id;
    protected String accountId;
    protected Boolean enabled;
    protected String name;
    protected String address;
    protected String serialNumber;
    protected String firmwareVersion;
    protected Boolean shared;
    protected String description;
    protected GatewayModel model;
    protected Map<String, Map<String, Object>> settings = new HashMap<String, Map<String, Object>>();

    public Gateway(){

    }


    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public boolean isNew() {
        return StringUtils.isEmpty(this.id);
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Boolean isShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GatewayModel getModel() {
        return model;
    }

    public void setModel(GatewayModel model) {
        this.model = model;
    }

    public Map<String, Map<String, Object>> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Map<String, Object>> settings) {
        this.settings = settings;
    }

    @Override
    public void merge(Gateway gateway) {
        String description = gateway.getDescription();
        if (description != null) {
            setDescription(description);
        }
        String serialNumber = gateway.getSerialNumber();
        if (serialNumber != null) {
            setSerialNumber(serialNumber);
        }
        String firmwareVersion = gateway.getFirmwareVersion();
        if (firmwareVersion != null) {
            setFirmwareVersion(firmwareVersion);
        }
        Boolean enabled = gateway.getEnabled();
        if (enabled != null) {
            setEnabled(enabled);
        }
        String name = gateway.getName();
        if (name != null) {
            setName(name);
        }
        String address = gateway.getAddress();
        if (address != null) {
            setAddress(address);
        }
        Boolean shared = gateway.isShared();
        if (shared != null) {
            setShared(shared);
        }

        GatewayModel model = gateway.getModel();
        if (model != null) {
            setModel(model);
        }

        mergeSettings(gateway.getSettings(), this.settings);
    }
}
