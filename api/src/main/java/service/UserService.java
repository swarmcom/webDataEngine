package service;

import domain.User;

public interface UserService {
    User getUser(String userName);
    void createUser(String userName, String password);
}
