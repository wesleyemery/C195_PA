package Database;

import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
