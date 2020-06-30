package Utils;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBCity {
    public static Integer getCityId(String city, Integer countryId) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT cityId FROM city WHERE city = ? AND countryId = ?;");
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
            ps.setTimestamp(3, now());
            ps.setString(4, "admin");
            ps.setTimestamp(5, now());
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

    public static Timestamp now() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }
}
