package View_Controller;

import Utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.text.normalizer.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class addCustomerController {
    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerPhoneTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private TextField customerPostalCodeTextField;

    @FXML
    private TextField customerCityTextField;

    @FXML
    private Button saveCustomerBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelHandler(ActionEvent event) {
        String message = "Are you sure you want to cancel?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT: Cancel");
        alert.setHeaderText("Confirm");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            backToMain(event);
        }
    }

    @FXML
    void saveCustomerHandler(ActionEvent event) {
        String name = customerNameTextField.getText().trim();
        String phone = customerPhoneTextField.getText().trim();
        String address = customerAddressTextField.getText().trim();
        String city = customerCityTextField.getText().trim();
        String zipCode = customerPostalCodeTextField.getText().trim();


        try{

            PreparedStatement ps = DBConnection.getConnection().prepareStatement("INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,address);
            ps.setString(2, "none");
            ps.setString(3, city);
            ps.setString(4, "none");
            ps.setString(5, phone);
            ps.setString(6, Utility.Time.now()));
            ps.setString(7, "app user");
            ps.setString(8, getTimestamp());
            ps.setString(9, "app user");
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    private void backToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            MainScreenController controller = new MainScreenController();
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
}
