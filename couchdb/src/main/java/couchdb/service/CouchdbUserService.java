package couchdb.service;

import couchdb.dao.CouchdbUserRepository;
import couchdb.domain.CouchdbUser;
import api.domain.User;
import api.service.UserService;
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
    public void createUser(String userName, String password, List<String> roles) {
        userRepository.create(new CouchdbUser(userName, password));
    }

    @Override
    public List<? extends User> getUsers() {
        return null;
    }
}
