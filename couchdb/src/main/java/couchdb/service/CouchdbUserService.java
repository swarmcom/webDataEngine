package couchdb.service;

import couchdb.dao.CouchdbUserRepository;
import couchdb.domain.CouchdbUser;
import domain.User;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouchdbUserService implements UserService {

    @Autowired
    CouchdbUserRepository userRepository;

    @Override
    public CouchdbUser getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void createUser(String userName, String password) {
        userRepository.create(new CouchdbUser(userName, password));
    }

    @Override
    public List<? extends User> getUsers() {
        return null;
    }
}
