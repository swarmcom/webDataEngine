package api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meeting extends BeanDomain<Meeting>{

    protected String id;

    protected String meetingName;

    protected String password;

    protected String accountId;

    protected Map<String, Map<String, Object>> settings = new HashMap<String, Map<String, Object>>();

    public Meeting() {
    }

    public Meeting(String accountId, String meetingName, String password) {
        this.accountId = accountId;
        this.meetingName = meetingName;
        this.password = password;
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

    public String getMeetingName() { return meetingName; }

    public String getPassword() {
        return password;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Map<String, Map<String, Object>> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Map<String, Object>> settings) {
        this.settings = settings;
    }

    @Override
    public void merge(Meeting meeting) {
        String id = meeting.getId();
        String accountId = meeting.getAccountId();
        String meetingName = meeting.getMeetingName();
        String meetingPassword = meeting.getPassword();

        if (id != null) {
            setId(id);
        }

        if (accountId != null) {
            setAccountId(accountId);
        }

        if (meetingName != null) {
            setMeetingName(meetingName);
        }
        if (meetingPassword != null) {
            setPassword(meetingPassword);
        }

        mergeSettings(meeting.getSettings(), this.settings);
    }
}

