package api.service;

import api.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {
    User getUser(String accountId, String userName);

    User createUser(String accountId, String userName, String password, Set<String> roles);

    User saveUser(String accountId, User user);

    public List<? extends User> getUsers(String accountId);

    User getUserById (String accountId, String userId);

    Long deleteUser(String accountId, String userName);

    Long deleteUsers(String accountId, Collection<String> userIds);

    Long deleteUsers(String accountId);
}
