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
import java.util.List;

@Component
public class MongoUserService implements UserService {

    @Autowired
    MongoUserRepository userRepository;

    @Autowired
    DBCollection userCollection;

    @PostConstruct
    public void init() {
        userCollection.createIndex(new BasicDBObject("userName", 1).append("unique", true));
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(String userName, String password, List<String> roles) {
        userRepository.save(new User(userName, password, roles));
    }

    @Override
    public List<? extends User> getUsers() {
        return userRepository.findAll();
    }
}
