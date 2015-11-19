package service;

import dao.UserRepository;
import domain.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public MongoUser getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(String userName, String password) {
        userRepository.save(new MongoUser(userName, password));
    }
}
