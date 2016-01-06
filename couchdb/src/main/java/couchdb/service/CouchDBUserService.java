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
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(String userName, String password, List<String> roles) {
        userRepository.create(new CouchDBUser(userName, password, roles));
    }

    @Override
    public List<? extends User> getUsers() {
        return userRepository.getAllUsers();
    }
}
