package api.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Phone {
    protected String id;
    protected String accountId;
    protected String serialNumber;
    protected String description;
    protected String firmwareVersion;
    protected List<String> lines = new ArrayList<String>();
    protected HashMap<String, Object> settings = new HashMap<String, Object>();

    public Phone() {
        this.firmwareVersion = "4.0";
        this.description = "Anonymous phone";
        this.serialNumber = "123456789012";
        this.accountId = "Anonymous";
        settings.put("mama", "Rodica");
        settings.put("mama/varsta", 68);
        List<String> brothers = new ArrayList<String>();
        brothers.add("Vladone");
        brothers.add("Andriuca");
        settings.put("brothers", brothers);
    }

    public Phone(String accountId, String serialNumber, String description, String firmwareVersion) {
        this.serialNumber = serialNumber;
        this.firmwareVersion = firmwareVersion;
        this.description = description;
        this.accountId = accountId;
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
}
