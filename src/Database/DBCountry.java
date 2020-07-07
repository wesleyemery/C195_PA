package Database;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBCountry {
    public static Integer getCountryId(String country) {
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT countryId, country FROM country WHERE country = ?;");
            ps.setString(1, country);

            ResultSet rs = ps.executeQuery();

            Integer id = null;

            if(rs.next()) {
                id = rs.getInt("countryId");
            }

            rs.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Integer addToCountryTable(String country) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO country(country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES" +
                    "(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, country);
            ps.setString(2, Utils.Time.dateTime());
            ps.setString(3, "admin");
            ps.setString(4, Utils.Time.dateTime());
            ps.setString(5, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            Integer id = null;
            if (ps.getUpdateCount() == 0)
                System.out.println("Country creation failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added to country table successfully");
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



}
