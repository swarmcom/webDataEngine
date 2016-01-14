package api.service;

import java.util.List;

public interface MultiUserService extends UserService {
    public void createUser(String userName, String password, List<String> roles);
}
