package Database;


import Model.Address;
import Model.Customer;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class DBAddress {
    public static Integer getAddressId(String address, Integer cityId) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT addressId FROM address WHERE address = ? AND cityId = ?");
            ps.setString(1, address);
            ps.setInt(2, cityId);

            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if(rs.next()) {
                id = rs.getInt("addressId");
            }

            rs.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer getAddressIdFromCustomer(String name) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT addressId FROM address WHERE customerName=?");
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if(rs.next()) {
                id = rs.getInt("addressId");
            }

            rs.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Integer addToAddressTable(String address, Integer cityId, String postalCode, String phoneNumber){

        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,address);
            ps.setString(2, "None");
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phoneNumber);
            ps.setString(6, Utils.Time.dateTime());
            ps.setString(7, "admin");
            ps.setString(8, Utils.Time.dateTime());
            ps.setString(9, "admin");

            ps.executeUpdate();
            if(ps.getUpdateCount() == 0)
                System.out.println("Address creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to address table successfully");
                alert.showAndWait();
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();

            Integer id = null;

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

    public static Integer updateToAddressTable(Integer addressId, String address, Integer cityId, String postalCode, String phoneNumber) {
        String query = "update address set address=?,  cityId=?, postalCode=?, phone=? " +
                "where addressId=?";
        try {
            PreparedStatement ps = DBConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, address);
            ps.setInt(2, cityId);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, addressId);

            ps.executeUpdate();

            if (ps.getUpdateCount() == 0)
                System.out.println("Address creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Updated to address table successfully");
                alert.showAndWait();
            }
            ps.close();
            return addressId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
