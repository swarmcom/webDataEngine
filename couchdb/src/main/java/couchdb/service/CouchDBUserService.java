package couchdb.service;

import api.domain.User;
import couchdb.dao.CouchDBUserRepository;
import api.service.UserService;
import couchdb.domain.CouchDBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class CouchDBUserService implements UserService {

    @Autowired
    CouchDBUserRepository userRepository;

    @Override
    public User getUser(String accountId,String userName) {
        return userRepository.findByAccountIdAndUserName(accountId, userName);
    }

    @Override
    public User createUser(String accountId, String userName, String password, Set<String> roles) {
        userRepository.create(new CouchDBUser(accountId, userName, password, roles));
        //TODO return saved user
        return null;
    }

    @Override
    public User saveUser(User user) {
        userRepository.save((CouchDBUser)user);
        //TODO return saved user
        return null;
    }

    @Override
    public List<? extends User> getUsers(String accountId) {
        return userRepository.getAllUsers(accountId);
    }

    @Override
    public User getUserById(String accountId, String userId) {
        return null;
    }

    @Override
    public Long deleteUser(String accountId, String userName) {
        return null;
    }

    @Override
    public Long deleteUsers(String accountId, Collection<String> userIds) {
        return null;
    }
}
