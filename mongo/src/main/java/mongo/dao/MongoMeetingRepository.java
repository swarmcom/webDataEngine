package mongo.dao;

import api.domain.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface MongoMeetingRepository extends MongoRepository<Meeting, String> {

    Meeting findByAccountIdAndMeetingName(String accountId, String meetingName);

    List<Meeting> findByAccountId(String accountId);

    Meeting findByAccountIdAndId(String accountId, String id);

    Long deleteByAccountIdAndMeetingName(String accountId, String meetingName);

    Long deleteByAccountIdAndIdIn(String accountId, Collection<String> ids);

    Long deleteByAccountId(String accountId);
}
