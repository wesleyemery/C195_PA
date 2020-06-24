package Main;

import Utils.DBConnection;
import Utils.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utils.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/LoginView.fxml"));
        primaryStage.setTitle("Scheduling System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }


    public static void main(String[] args) throws SQLException {

        /*DataGenerator data = new DataGenerator();
        data.populateAppointmentsTable();*/
        Connection connection = DBConnection.getConnection(); //Connect to DB
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        //SQL insert statement
        /*String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES('US', '2020-06-17 00:00:00', 'admin', 'admin')";

        //SQL update statement
        String updateStatement = "UPDATE country SET country = 'Japan' WHERE country = 'Canada'";

        //SQL delete statement
        String deleteStatement = "DELETE from country WHERE country = 'Japan'";

        //Execute SQL Statement
        statement.execute(insertStatement);
        //statement.execute(updateStatement);

        //Confirm rows effected
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " row(s) affected");
        else
            System.out.println("No change!");


        String selectStatement = "SELECT * FROM country";
        statement.execute(selectStatement); //Execute statement
        ResultSet rs = statement.getResultSet();

        // Forward Scroll ResultSet
        while (rs.next())
        {
            int countryID = rs.getInt("countryId");
            String countryName = rs.getString("country");
            LocalDate date = rs.getDate("createDate").toLocalDate();
            LocalTime time = rs.getTime("createDate").toLocalTime();
            String createdBy = rs.getString("createdBy");
            LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();

            //Display Record
            System.out.println(countryID + " | " + countryName + " | " + date + "  " + time + " | " + createdBy + " | " + lastUpdate );

        }*/

        launch(args);
        DBConnection.closeConnection();
    }
}
