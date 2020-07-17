package Database;

import Controller.addAppointmentController;
import Model.Customer;
import Model.User;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;

import static Utils.Time.now;

public class DBAppointment {


    //2020-08-22 15:00:00.0


   public static boolean addToAppointmentTable(Integer customerId, String title, String startTime, String endTime, String type,  String description) {
        User user = new User();
        addAppointmentController add = new addAppointmentController();
        Integer currentUserId = user.getUserId();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO appointment ("
                            + "customerId, "
                            + "userId, "
                            + "title, "
                            + "description, "
                            + "location, "
                            + "contact, "
                            + "type, "
                            + "url, "
                            + "start, "
                            + "end, "
                            + "createDate, "
                            + "createdBy, "
                            + "lastUpdate, "
                            + "lastUpdateBy) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, customerId);
            ps.setInt(2, 1);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, "");
            ps.setString(6, "");
            ps.setString(7, type);
            ps.setString(8, "");
            ps.setString(9, startTime);
            ps.setString(10, endTime);
            ps.setString(11, Utils.Time.getTimestamp());
            ps.setString(12, "admin");
            ps.setString(13, Utils.Time.getTimestamp());
            ps.setString(14, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            boolean success = false;

            if (generatedKeys.next()) {
                success = true;
            }

            ps.close();
            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Integer updateToAppointmentTable(int customerId, String title, String startTime, String endTime, String type, int appointmentId) {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("UPDATE appointment SET customerId = ?, "
                    +"title = ?, "
                    +"start = ?, "
                    +"end = ?, "
                    +"type = ?, "
                    +"description = ? "
                    +"WHERE "
                    +"appointmentId = ?");
            ps.setInt(1, customerId);
            ps.setString(2, title);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            ps.setString(5, type);
            ps.setInt(6, appointmentId);

            ps.executeUpdate();


            if (ps.getUpdateCount() == 0)
                System.out.println("Appointment update failed");
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Updated to appointment table successfully");
                alert.showAndWait();
            }

            ps.close();
            return appointmentId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
