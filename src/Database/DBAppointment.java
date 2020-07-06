package Utils;

import Model.Customer;
import Model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DBAppointment {


   /* public static boolean addToAppointmentTable(Customer customer, String title, String type, String date, String startTime, String endTime, String description) {
        User user = new User();
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

            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, currentUserId);
            ps.setString(3, title); //title
            ps.setString(4, description); //description
            ps.setString(5, "");
            ps.setString(6, ""); //contact
            ps.setString(7, type);
            ps.setString(8, ""); //url

            String startDateTime = date + " " + startTime;

            LocalDateTime startLocalDateTime = LocalDateTime.parse(
                    startDateTime,
                    DateTimeFormatter.ofPattern("yyyy-M-d h:mm a"));

            ZonedDateTime startZonedDateTime = startLocalDateTime.atZone(Objects.requireNonNull(getZoneId(location)));
            Timestamp startTimestamp = Timestamp.from(startZonedDateTime.toInstant());

            String endDateTime = date + " " + endTime;

            LocalDateTime endLocalDateTime = LocalDateTime.parse(
                    endDateTime,
                    DateTimeFormatter.ofPattern("yyyy-M-d h:mm a"));

            ZonedDateTime endZonedDateTime = endLocalDateTime.atZone(Objects.requireNonNull(getZoneId(location)));
            Timestamp endTimestamp = Timestamp.from(endZonedDateTime.toInstant());

            ps.setTimestamp(9, startTimestamp);
            ps.setTimestamp(10, endTimestamp);

            ps.setTimestamp(11, now());
            ps.setString(12, "admin");
            ps.setTimestamp(13, now());
            ps.setString(14, "admin");

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            boolean success = false;

            if (generatedKeys.next()) {
                success = true;
            }

            ps.close();
            return success;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }*/

    public static Timestamp now() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }


}
