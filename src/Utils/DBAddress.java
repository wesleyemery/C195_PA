package Utils;


import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class DBAddress {

    public Integer addToAddressTable(String address, Integer cityId, String postalCode, String phoneNumber){

        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,address);
            ps.setString(2, "");
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phoneNumber);
            ps.setTimestamp(6, now());
            ps.setString(7, "admin");
            ps.setTimestamp(8, now());
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

    public static Timestamp now() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }
}
