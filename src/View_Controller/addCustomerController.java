package View_Controller;

import Utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sun.text.normalizer.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {
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
    private TextField customerCountryTextField;

    @FXML
    private CheckBox customerActiveCheckBox;
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
        String country = customerCountryTextField.getText().trim();
        String zipCode = customerPostalCodeTextField.getText().trim();
        Integer active = customerActiveCheckBox.isSelected()?1:0;



        if (customerNameTextField.getText().isEmpty() || customerAddressTextField.getText().isEmpty() || customerCityTextField.getText().isEmpty() || customerPostalCodeTextField.getText().isEmpty() ||  customerPhoneTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter data in all fields!");
            alert.showAndWait();
        }else {
            Integer countryId = DBCountry.getCountryId(country);
            if ( countryId == null )
                countryId = DBCountry.addToCountryTable(country);


            Integer cityId = DBCity.getCityId(city, countryId);
            //System.out.println("Cityid = " + cityId);

            if ( cityId == null ){
                cityId = DBCity.addToCityTable(city, countryId);
            }

            Integer addressId = DBAddress.getAddressId(address, cityId);

            if (addressId == null) {
                addressId = DBAddress.addToAddressTable(address, cityId, zipCode, phone);
            }

            Integer customerId = DBCustomer.addToCustomerTable(name, addressId, active);

            if (customerId == null) {
                customerId = DBCustomer.addToCustomerTable(name, addressId, active);
            }

            backToMain(event);


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
