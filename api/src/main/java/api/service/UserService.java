package api.service;

import api.domain.User;

import java.util.List;

public interface UserService {
    User getUser(String userName);

    void createUser(String userName, String password, List<String> roles);

    public List<? extends User> getUsers();
}
