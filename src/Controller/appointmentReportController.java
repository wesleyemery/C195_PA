package Controller;

import Database.DBConnection;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class appointmentReportController implements Initializable {

    @FXML
    private TextArea appointmentTextArea;

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) {
        backToMain(event);
    }

    private void backToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/mainScreen.fxml"));
            mainScreenController controller = new mainScreenController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAppointmentReport() {

        int id = 1;
        try {
            Statement sm = DBConnection.getConnection().createStatement();
            String query = "SELECT COUNT(DISTINCT type) FROM appointment WHERE userId = '"+ id +"';";
            ResultSet rs = sm.executeQuery(query);
            StringBuilder sbuf = new StringBuilder();
            sbuf.append(String.format("Number of Appointment Types: "));

            while (rs.next()) {
                sbuf.append(rs.getInt("count(distinct type)")).toString();}
            sm.close();
            appointmentTextArea.setText(sbuf.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAppointmentReport();
    }
}
