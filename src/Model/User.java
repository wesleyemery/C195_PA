package Model;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class User {

    private Integer userId;
    private String userName;
    private String password;
    private Boolean active;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {};

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
