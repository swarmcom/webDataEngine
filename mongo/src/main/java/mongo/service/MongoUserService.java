package mongo.service;

import api.domain.User;
import mongo.dao.MongoUserRepository;
import mongo.domain.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.service.UserService;

import java.util.List;

@Component
public class MongoUserService implements UserService {

    @Autowired
    MongoUserRepository userRepository;

    @Override
    public MongoUser getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(String userName, String password, List<String> roles) {
        userRepository.save(new MongoUser(userName, password, roles));
    }

    @Override
    public List<? extends User> getUsers() {
        return userRepository.findAll();
    }
}
