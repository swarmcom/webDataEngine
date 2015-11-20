package mongo.service;

import mongo.dao.MongoUserRepository;
import mongo.domain.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class MongoUserService implements UserService {

    @Autowired
    MongoUserRepository userRepository;

    @Override
    public MongoUser getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(String userName, String password) {
        userRepository.save(new MongoUser(userName, password));
    }
}
