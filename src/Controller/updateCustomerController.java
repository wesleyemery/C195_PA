package Controller;

import Model.*;
import Database.DBAddress;
import Database.DBCity;
import Database.DBCountry;
import Database.DBCustomer;
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
            Integer countryId = this.customer.getCountryId();

            if(country != customer.getCountry()){
                countryId = DBCountry.getCountryId(country);
                if (countryId == null)
                    countryId = DBCountry.addToCountryTable(country);
            }

            //City Table
            Integer cityId = this.customer.getCityId();

            if(city != customer.getCity()){
                cityId = DBCity.getCityId(city, countryId);
                if (cityId == null){
                    cityId = DBCity.addToCityTable(city, countryId);}
                else{
                    cityId = DBCity.updateToCityTable(city,countryId,cityId);

                }
            }

            //Address Table
            //Integer addressId = DBAddress.getAddressIdFromCustomer(this.customer.getCustomerName());
            Integer addressId = this.customer.getAddressId();

            if (city != customer.getCity() || address != customer.getAddress() || phone != customer.getPhone() || zipCode != customer.getPostalCode())  {
                /*if (cityId != null)
                    addressId = DBAddress.getAddressId(address, cityId);*/
                if (addressId == null) {
                    addressId = DBAddress.addToAddressTable(address, cityId, zipCode, phone);
                } else{
                    addressId = DBAddress.updateToAddressTable(addressId, address, cityId, zipCode, phone);
                    }
            }

            //Customer Table
            Integer customerId = customer.getCustomerId();

            if(name != customer.getCustomerName() || addressId != customer.getAddressID() || active != customer.getActive()) {
                //Integer customerId = DBCustomer.getCustomerId(name, addressId);
                //if (customerId != null)

                    customerId = DBCustomer.updateToCustomerTable(customerId, name, addressId, active);
            }

            backToMain(event);

        }
    }

    private void backToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainScreen.fxml"));
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

    public void populateCustomerFields(Customer selectedCustomer){
        this.customer = selectedCustomer;


        customerNameTextField.setText(selectedCustomer.getCustomerName());
        customerPhoneTextField.setText(selectedCustomer.getCustomerPhone());
        customerAddressTextField.setText((selectedCustomer.getCustomerAddress()));
        customerPostalCodeTextField.setText(selectedCustomer.getCustomerPostalCode());
        customerCityTextField.setText(selectedCustomer.getCustomerCity());
        customerCountryTextField.setText(selectedCustomer.getCustomerCountry());
        //customerActiveCheckBox.setSelected(selectedCustomer.getActive() == 1);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
