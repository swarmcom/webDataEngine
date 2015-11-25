package service;

import domain.User;

import java.util.List;

public interface UserService {
    User getUser(String userName);
    void createUser(String userName, String password);
    public List<? extends User> getUsers();
}
