package View_Controller;

import Model.*;
import Utils.DBAddress;
import Utils.DBCity;
import Utils.DBCountry;
import Utils.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {
    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private TextField customerCountryTextField;

    @FXML
    private TextField customerPostalCodeTextField;

    @FXML
    private CheckBox customerActiveCheckBox;

    @FXML
    private TextField customerCityTextField;
    @FXML
    private Button cancelBtn;

    @FXML
    private TextField customerPhoneTextField;


    @FXML
    private Button updateCustomerBtn;

    Customer customer;
    City city;
    Country country;
    Address address;

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
    void updateCustomerHandler(ActionEvent event) {
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
            //Country Table
            Integer countryId = customer.getCountryId();

            if(!(country == customer.getCountry())){
                countryId = DBCountry.getCountryId(country);
                if (!(countryId != null))
                    countryId = DBCountry.addToCountryTable(country);
            }

            //City Table
            Integer cityId = customer.getCityId();

            if(!(city == customer.getCity())){
                cityId = DBCity.getCityId(city, countryId);
                if (!(cityId != null))
                    cityId = DBCity.addToCityTable(city, cityId);
            }

            //Address Table
            Integer addressId = customer.getAddressId();

            if (!(city == customer.getCity()) || address == customer.getAddress() || phone != customer.getPhone() || zipCode != customer.getPostalCode())  {
                addressId = DBAddress.getAddressId(address, cityId);
                if (!(addressId != null))
                    addressId = DBAddress.addToAddressTable(address, cityId, zipCode, phone);
            }

            //Customer Table
            Integer customerId = customer.getCustomerId();

            if(!(name == customer.getCustomerName() || addressId == customer.getAddressID() || active == customer.getActive())) {
                customerId = DBCustomer.getCustomerId(name, addressId);
                if (!(customerId != null))
                    customerId = DBCustomer.updateCustomerTable(customerId, name, addressId, active);
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

    public void populateCustomerFields(Customer customer){
        customer = customer;
        /*address = address;
        city = city;
        country = country;*/

        customerNameTextField.setText(customer.getCustomerName());
        /*customerPhoneTextField.setText(address.getPhone());
        customerAddressTextField.setText((address.getAddress()));
        customerPostalCodeTextField.setText(address.getPostalCode());
        customerCityTextField.setText(city.getCity());
        customerCountryTextField.setText(country.getCountry());*/
       // customerActiveCheckBox.setSelected(customer.getActive() == 1);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
