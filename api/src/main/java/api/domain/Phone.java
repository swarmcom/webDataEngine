package api.domain;

public class Phone {
    protected String id;
    protected String accountId;
    protected String serialNumber;
    protected String description;
    protected String firmwareVersion;

    public Phone() {
        this.firmwareVersion = "4.0";
        this.description = "Anonymous phone";
        this.serialNumber = "123456789012";
        this.accountId = "Anonymous";
    }

    public Phone(String accountId, String firmwareVersion, String description, String serialNumber) {
        this.firmwareVersion = firmwareVersion;
        this.description = description;
        this.serialNumber = serialNumber;
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
}
