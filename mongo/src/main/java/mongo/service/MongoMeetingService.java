package mongo.service;

import api.domain.Meeting;
import api.service.MeetingService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.dao.MongoMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class MongoMeetingService implements MeetingService {

    @Autowired
    MongoMeetingRepository meetingRepository;

    @Autowired
    DBCollection meetingCollection;

    @PostConstruct
    public void init() {
        meetingCollection.createIndex(new BasicDBObject("accountId", 1));
        meetingCollection.createIndex(new BasicDBObject("meetingName", 1));
    }

    @Override
    public Meeting getMeeting(String accountId, String meetingName) {
        return meetingRepository.findByAccountIdAndMeetingName(accountId, meetingName);
    }

    @Override
    public Meeting createMeeting(String accountId, String meetingName, String password) {
        return meetingRepository.save(new Meeting(accountId, meetingName, password));
    }

    @Override
    public Meeting saveMeeting(String accountId, Meeting meeting) {
        meeting.setAccountId(accountId);
        return meetingRepository.save(meeting);
    }

    @Override
    public List<? extends Meeting> getMeetings(String accountId) {
        System.out.println("getMeetings are: " + accountId);
        return meetingRepository.findByAccountId(accountId);
    }

    @Override
    public Meeting getMeetingById(String accountId, String meetingId) {
        return meetingRepository.findByAccountIdAndId(accountId, meetingId);
    }

    @Override
    public Long deleteMeeting(String accountId, String meetingName) {
        return meetingRepository.deleteByAccountIdAndMeetingName(accountId, meetingName);
    }

    @Override
    public Long deleteMeetings(String accountId, Collection<String> meetingIds) {
        return meetingRepository.deleteByAccountIdAndIdIn(accountId, meetingIds);
    }

    @Override
    public Long deleteMeetings(String accountId) {
        return meetingRepository.deleteByAccountId(accountId);
    }

}

