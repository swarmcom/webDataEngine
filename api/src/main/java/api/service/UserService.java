package api.service;

import api.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User getUser(String accountId, String userName);

    User createUser(String accountId, String userName, String password, List<String> roles);

    User saveUser(User user);

    public List<? extends User> getUsers(String accountId);

    User getUserById (String accountId, String userId);

    Long deleteUser(String accountId, String userName);

    Long deleteUsers(String accountId, Collection<String> userIds);
}
