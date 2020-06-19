package Main;

import Utils.DBConnection;
import Utils.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/LoginView.fxml"));
        primaryStage.setTitle("Scheduling System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }


    public static void main(String[] args) throws SQLException {


        Connection connection = DBConnection.getConnection();
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();

        //SQL insert statement
        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES('US', '2020-06-17 00:00:00', 'admin', 'admin')";

        //Execute SQL Statement
        statement.execute(insertStatement);

        //Confirm rows effected
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " row(s) affected");
        else
            System.out.println("No change!");

        launch(args);
        DBConnection.closeConnection();
    }
}
