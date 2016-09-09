package api.domain;

import api.type.PhoneModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Phone extends BeanDomain<Phone> {
    protected String id;
    protected String accountId;
    protected String serialNumber;
    protected String description;
    protected String firmwareVersion;
    protected List<String> lines = new ArrayList<String>();
    protected Map<String, Map<String, Object>> settings = new HashMap<String, Map<String, Object>>();
    protected PhoneModel model;

    public Phone() {
    }

    public Phone(String accountId, String serialNumber, String description, String firmwareVersion) {
        this.serialNumber = serialNumber;
        this.firmwareVersion = firmwareVersion;
        this.description = description;
        this.accountId = accountId;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public Map<String, Map<String, Object>> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Map<String, Object>> settings) {
        this.settings = settings;
    }

    public PhoneModel getModel() {
        return model;
    }

    public void setModel(PhoneModel model) {
        this.model = model;
    }

    public void merge(Phone phone) {
        String description = phone.getDescription();
        if (description != null) {
            setDescription(description);
        }
        String serialNumber = phone.getSerialNumber();
        if (serialNumber != null) {
            setSerialNumber(serialNumber);
        }
        String firmwareVersion = phone.getFirmwareVersion();
        if (firmwareVersion != null) {
            setFirmwareVersion(firmwareVersion);
        }
        List<String> lines = phone.getLines();
        if (lines != null) {
            setLines(lines);
        }

        mergeSettings(phone.getSettings(), this.settings);
    }
}
