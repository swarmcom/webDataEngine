package models.domain;

import domain.User;

public class ModelUser implements User {
    private String userName;
    private String password;

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
