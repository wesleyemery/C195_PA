package Database;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBCustomer {


    /*public static Integer getCustomerId(String customerName, Integer addressId) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT customerId FROM customer WHERE customerName = ? AND addressId = ?;");
            ps.setString(1, customerName);
            ps.setInt(2, addressId);

            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if(rs.next()) {
                id = rs.getInt("customerId");
            }

            rs.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/


    public static Integer addToCustomerTable(String customerName, Integer addressId, Integer active) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, active);
            ps.setString(4, Utils.Time.dateTime());
            ps.setString(5, "admin");
            ps.setString(6, Utils.Time.dateTime());
            ps.setString(7, "admin");

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

            ps.close();
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

            Integer id = null;

            if (rs.next()) {
                id = rs.getInt("customerId");
            }

            rs.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}

