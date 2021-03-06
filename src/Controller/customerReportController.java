package Controller;


import Database.DBConnection;
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

public class customerReportController implements Initializable{

    @FXML
    private TextArea customerTextArea;

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

    public void getCustomerReport() {
        try {
            Statement sm = DBConnection.getConnection().createStatement();
            String query = "SELECT COUNT(customerId) FROM customer";
            ResultSet rs = sm.executeQuery(query);
            StringBuilder sbuf = new StringBuilder();
            sbuf.append("Number of Customers: ");
            while (rs.next()) {
                sbuf.append(rs.getInt("count(customerId)"));}
            sm.close();
            customerTextArea.setText(sbuf.toString());
        } catch (SQLException e) {
            e.printStackTrace();}

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getCustomerReport();
    }
}
