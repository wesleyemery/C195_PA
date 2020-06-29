package Utils;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBCustomer {
    public Integer addToCustomerTable(String customerName, Integer addressId, Integer active) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES" +
                    "(?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active);
            ps.setTimestamp(6, now());
            ps.setString(7, "admin");
            ps.setTimestamp(8, now());
            ps.setString(9, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            Integer id = null;
            if (ps.getUpdateCount() == 0)
                System.out.println("Customer creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to customer table successfully");
                alert.showAndWait();
            }
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            ps.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Timestamp now() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }
}
