package Database;

import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {
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

    public static Integer queryUserIdbyName(String user) {

        String query = "SELECT userId "
                + "FROM user where userName = ?;";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if (rs.next()) {
                 id = rs.getInt("userId");

            }

            rs.close();
            return id;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer queryUserId() {

        String query = "SELECT * "
                + "FROM user";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if (rs.next()) {
                id = rs.getInt("userId");

            }

            rs.close();
            return id;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
