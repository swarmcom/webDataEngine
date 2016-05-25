package api.service;

import api.domain.User;

import java.util.Collection;
import java.util.List;

public interface MultiUserService extends UserService {
    public User createUser(String userName, String password, List<String> roles);

    User getUser(String userName);

    public List<? extends User> getUsers();

    User getUserById (String userId);

    Long deleteUser(String userName);

    Long deleteUsers(Collection<String> userIds);
}
