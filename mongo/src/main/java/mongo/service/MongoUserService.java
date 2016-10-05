package mongo.service;

import api.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import mongo.dao.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import api.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class MongoUserService implements UserService {

    @Autowired
    MongoUserRepository userRepository;

    @Autowired
    DBCollection userCollection;

    @PostConstruct
    public void init() {
        userCollection.createIndex(new BasicDBObject("accountId", 1));
        userCollection.createIndex(new BasicDBObject("userName", 1));
    }

    @Override
    public User getUser(String accountId, String userName) {
        return userRepository.findByAccountIdAndUserName(accountId, userName);
    }

    @Override
    public User createUser(String accountId, String userName, String password, Set<String> roles) {
        return userRepository.save(new User(accountId, userName, password, roles));
    }

    @Override
    public User saveUser(String accountId, User user) {
        user.setAccountId(accountId);
        return userRepository.save(user);
    }

    @Override
    public List<? extends User> getUsers(String accountId) {
        return userRepository.findByAccountId(accountId);
    }

    @Override
    public User getUserById(String accountId, String userId) {
        return userRepository.findByAccountIdAndId(accountId, userId);
    }

    @Override
    public Long deleteUser(String accountId, String userName) {
        return userRepository.deleteByAccountIdAndUserName(accountId, userName);
    }

    @Override
    public Long deleteUsers(String accountId, Collection<String> userIds) {
        return userRepository.deleteByAccountIdAndIdIn(accountId, userIds);
    }

}
