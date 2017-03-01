package api.service;

import api.domain.Meeting;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MeetingService {
    Meeting getMeeting(String accountId, String meetingName);

    Meeting createMeeting(String accountId, String meetingName, String password);

    Meeting saveMeeting(String accountId, Meeting meeting);

    public List<? extends Meeting> getMeetings(String accountId);

    Meeting getMeetingById (String accountId, String meetingId);

    Long deleteMeeting(String accountId, String meetingName);

    Long deleteMeetings(String accountId, Collection<String> meetingIds);

    Long deleteMeetings(String accountId);
}
