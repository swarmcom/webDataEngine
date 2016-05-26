package api.service;

import api.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MultiUserService extends UserService {
    public User createUser(String userName, String password, Set<String> roles);

    User getUser(String userName);

    public List<? extends User> getUsers();

    User getUserById (String userId);

    Long deleteUser(String userName);

    Long deleteUsers(Collection<String> userIds);
}
