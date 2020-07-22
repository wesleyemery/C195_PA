package Database;

import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {

    private static ObservableList<User> userArray = FXCollections.observableArrayList();

    public static ObservableList<User> queryUsers() {
        ObservableList<User> allUsers =  FXCollections.observableArrayList();

        String query = "SELECT user.userName, user.password "
                + "FROM user ";

        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(query);
            while (rs.next()) {
                String user = rs.getString("user.userName");
                String password = rs.getString("user.password");

                allUsers.add(new User(user, password));
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }
    public static ObservableList<User> queryAllUserNames(){
        String query = "SELECT userId, userName FROM user;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                userArray.add(new User(userId, userName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArray;
    }


    public static Integer queryUserId() {

        String query = "SELECT * "
                + "FROM user";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Integer userid = null;

            if (rs.next()) {
                userid = rs.getInt("userId");

            }

            return userid;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer queryUserIdfromName(String userName) {

        String query = "SELECT * "
                + "FROM user WHERE userName = '" + userName + "';";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Integer userid = null;

            if (rs.next()) {
                userid = rs.getInt("userId");

            }

            return userid;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
