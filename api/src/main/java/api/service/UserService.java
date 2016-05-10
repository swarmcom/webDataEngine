package api.service;

import api.domain.User;

import java.util.List;

public interface UserService {
    User getUser(String accountId, String userName);

    void createUser(String accountId, String userName, String password, List<String> roles);

    void saveUser(User user);

    public List<? extends User> getUsers(String accountId);
}
