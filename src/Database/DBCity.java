package Database;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBCity {
    public static Integer getCityId(String city) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT cityId FROM city WHERE city = ?");
            ps.setString(1, city);


            ResultSet rs = ps.executeQuery();

            Integer cityid = null;

            if(rs.next()) {
                cityid = rs.getInt("cityId");
            }

            return cityid;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Integer getCityIdFromAddressId(Integer addressId) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT cityId FROM address WHERE addressId=?");

            ps.setInt(1, addressId);

            ResultSet rs = ps.executeQuery();

            Integer cityid = null;

            if(rs.next()) {
                cityid = rs.getInt("cityId");
            }

            return cityid;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer addToCityTable(String city, Integer countryId) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO city(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES" +
                    "(?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, city);
            ps.setInt(2, countryId);
            ps.setTimestamp(3, Utils.Time.now());
            ps.setString(4, "admin");
            ps.setTimestamp(5, Utils.Time.now());
            ps.setString(6, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            Integer cityid = null;
            if (ps.getUpdateCount() == 0)
                System.out.println("City creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to City table successfully");
                alert.showAndWait();
            }
            if (generatedKeys.next()) {
                cityid = generatedKeys.getInt(1);
            }

            return cityid;

        } catch (
                SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer updateToCityTable(String city, Integer countryId, Integer cityId) {
        String query = "update city set city=?, countryId=? " +
                "where cityId=?";
        try {
            PreparedStatement ps = DBConnection.getInstance().connection().prepareStatement(query);
            ps.setString(1, city);
            ps.setInt(2, countryId);
            ps.setInt(3, cityId);

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
            return cityId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
