package Model;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class User {

    private int userID;
    private String userName;
    private Boolean active;

    public User(int userID, String userName, Boolean active) {
        this.userID = userID;
        this.userName = userName;
        this.active = active;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
