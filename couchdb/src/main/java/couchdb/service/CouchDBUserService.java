package couchdb.service;

import api.domain.User;
import couchdb.dao.CouchDBUserRepository;
import api.service.UserService;
import couchdb.domain.CouchDBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouchDBUserService implements UserService {

    @Autowired
    CouchDBUserRepository userRepository;

    @Override
    public User getUser(String accountId,String userName) {
        return userRepository.findByAccountIdAndUserName(accountId, userName);
    }

    @Override
    public void createUser(String accountId, String userName, String password, List<String> roles) {
        userRepository.create(new CouchDBUser(accountId, userName, password, roles));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save((CouchDBUser)user);
    }

    @Override
    public List<? extends User> getUsers(String accountId) {
        return userRepository.getAllUsers(accountId);
    }
}
