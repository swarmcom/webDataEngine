package api.service;

import java.util.List;

public interface MultiUserService extends UserService {
    void createUser(String tenantId, String userName, String password, List<String> roles);
}
