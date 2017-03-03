package tenancy.service;


import api.domain.Meeting;
import api.service.MultiService;
import api.service.MeetingService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class MultiTenantMeetingService implements MeetingService {
    @Inject
    MultiService multiService;

    @Override
    public Meeting getMeeting(String accountId, String meetingName) {
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.getMeeting(accountId, meetingName);
    }

    @Override
    public Meeting createMeeting(String tenantId, String meetingName, String password, String duration, String participants) {
        MeetingService userService = multiService.getTenantMeetingService(tenantId);
        return userService.createMeeting(tenantId, meetingName, password, duration, participants);
    }

    @Override
    public Meeting saveMeeting(String accountId, Meeting meeting) {
        if (meeting.isNew()) {
            meeting.setAccountId(accountId);
        }
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.saveMeeting(accountId, meeting);
    }

    @Override
    public List<? extends Meeting> getMeetings(String accountId) {
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.getMeetings(accountId);
    }

    @Override
    public Meeting getMeetingById(String accountId, String meetingId) {
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.getMeetingById(accountId, meetingId);
    }

    @Override
    public Long deleteMeeting(String accountId, String meetingName) {
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.deleteMeeting(accountId, meetingName);
    }

    @Override
    public Long deleteMeetings(String accountId, Collection<String> meetingIds) {
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.deleteMeetings(accountId, meetingIds);
    }

    @Override
    public Long deleteMeetings(String accountId) {
        MeetingService userService = multiService.getTenantMeetingService(accountId);
        return userService.deleteMeetings(accountId);
    }
}

