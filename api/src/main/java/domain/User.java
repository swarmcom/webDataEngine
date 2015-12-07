package domain;

import java.util.List;

public interface User {
    String getUserName();
    String getPassword();
    List<String> getRoles();
}
