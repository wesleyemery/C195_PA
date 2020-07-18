package Database;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBCustomer {





    public static Integer addToCustomerTable(String customerName, Integer addressId, Integer active) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active);
            ps.setString(4, Utils.Time.getTimestamp());
            ps.setString(5, "admin");
            ps.setString(6, Utils.Time.getTimestamp());
            ps.setString(7, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            Integer customerid = null;
            if (ps.getUpdateCount() == 0)
                System.out.println("Customer creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to customer table successfully");
                alert.showAndWait();
            }
            if (generatedKeys.next()) {
                customerid = generatedKeys.getInt(1);
            }

            return customerid;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer updateToCustomerTable(Integer customerId, String customerName, Integer addressId, Integer active) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("UPDATE customer SET customerName = ?, "
                    +"addressId = ?, "
                    +"active = ?, "
                    +"createDate = ?, "
                    +"createdBy = ? "
                    +"WHERE "
                    +"customerId = ?");
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active);
            ps.setTimestamp(4, Utils.Time.now());
            ps.setString(5, "admin");
            ps.setInt(6, customerId);

            ps.executeUpdate();


            if (ps.getUpdateCount() == 0)
                System.out.println("Customer creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to customer table successfully");
                alert.showAndWait();
            }

            return customerId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Integer getCustomerId(String customerName) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT customerId FROM customer WHERE customerName = ?;");
            ps.setString(1, customerName);

            ResultSet rs = ps.executeQuery();

            Integer customerid = null;

            if (rs.next()) {
                customerid = rs.getInt("customerId");
            }

            return customerid;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCustomerName(int customerId) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT customerName FROM customer WHERE customerId = ?;");
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            String name = null;

            if (rs.next()) {
                name = rs.getString("customerName");
            }

            return name;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

