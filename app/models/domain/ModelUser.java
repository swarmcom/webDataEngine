package models.domain;

import api.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ModelUser implements User {
    private String userName;
    private String password;
    private List<String> roles = new ArrayList<String>();

    public ModelUser() {

    }

    public ModelUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }
}
