package Database;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBCity {
    public static Integer getCityId(String city, Integer countryId) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT cityId FROM city WHERE city = ? AND countryId = ?");
            ps.setString(1, city);
            ps.setInt(2, countryId);

            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if(rs.next()) {
                id = rs.getInt("cityId");
            }

            rs.close();
            return id;

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
            ps.setString(3, Utils.Time.dateTime());
            ps.setString(4, "admin");
            ps.setString(5, Utils.Time.dateTime());
            ps.setString(6, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            Integer id = null;
            if (ps.getUpdateCount() == 0)
                System.out.println("City creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to City table successfully");
                alert.showAndWait();
            }
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            ps.close();
            return id;

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
