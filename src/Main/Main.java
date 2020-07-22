package Main;

import Database.DBConnection;
import Database.DBUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/loginScreen.fxml"));
        primaryStage.setTitle("Scheduling System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }


    public static void main(String[] args) throws SQLException {

        /*String time = Utils.Time.dateTime();
        System.out.println("Date: " + time);

        ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);
        LocalDate parisDate = LocalDate.of(2019, 10, 26);
        LocalTime parisTime = LocalTime.of(01,00);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());

        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES(?, ?, ?, ?);";

        String countryName;
        String createDate = "2020-06-29 00:00:00";
        String createdBy = "admin";
        String lastUpdateBy = "admin";

        //Get User Input
        System.out.println("Enter a country: ");
        Scanner keyboard = new Scanner(System.in);
        countryName = keyboard.nextLine();

        DBQuery.setPreparedStatement(connection, insertStatement); //Create PreparedStatement

        PreparedStatement ps = DBQuery.getPreparedStatement();

        //Key-value Mapping
        ps.setString(1,countryName);
        ps.setString(2, createDate);
        ps.setString(3, createdBy);
        ps.setString(4, lastUpdateBy);
        ps.execute();

        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " rows impacted!");
        else
            System.out.println("No Change!");

        DBQuery.setPreparedStatement(connection);
        Statement statement = DBQuery.getStatement();

        DataGenerator data = new DataGenerator();
        data.addToCustomerTable("Matty Mac");
        String country, createDate, createdBy, lastUpdateBy;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter a Country: ");
        country = keyboard.nextLine();
        createDate = "2020-06-27 00:00:00";
        createdBy = "Wes Emery";
        lastUpdateBy = "Wes Emery";
        //SQL insert statement

        if(country.contains("'"))
            country = country.replace("'", "\\'");

        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy)" +
                "VALUES(" + "'" + country + "','" +  createDate + "','" + createdBy + "','" + lastUpdateBy + "'" +
                ")";

        //SQL update statement
        String updateStatement = "UPDATE country SET country = 'Japan' WHERE country = 'Canada'";

        //SQL delete statement
        String deleteStatement = "DELETE from country WHERE country = 'Japan'";

        //Execute SQL Statement
        statement.execute(insertStatement);
        statement.execute(updateStatement);

        //Confirm rows effected
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " row(s) affected");
        else
            System.out.println("No change!");



        String selectStatement = "SELECT * FROM country WHERE " + country;

        try {
            statement.execute(insertStatement); //Execute statement

            if (statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + " row(s) impacted!");
            else
                System.out.println("No change!");

            //ResultSet rs = statement.getResultSet();

            // Forward Scroll ResultSet
            while (rs.next()) {
                int countryID = rs.getInt("countryId");
                String countryName = rs.getString("country");
                LocalDate date = rs.getDate("createDate").toLocalDate();
                LocalTime time = rs.getTime("createDate").toLocalTime();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();

                //Display Record
                System.out.println(countryID + " | " + countryName + " | " + date + "  " + time + " | " + createdBy + " | " + lastUpdate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        Connection connection = DBConnection.getConnection(); //Connect to DB
        launch(args);
        DBConnection.closeConnection();
    }
}
