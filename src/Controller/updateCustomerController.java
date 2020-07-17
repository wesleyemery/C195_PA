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
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private CheckBox activeCb;

    @FXML
    private TextField cityTextField;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField phoneTextField;


    @FXML
    private Button updateCustomerButton;

    Customer customer;


    @FXML
    void cancelAction(ActionEvent event) {
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
    void updateCustomerAction(ActionEvent event) {
        String name = nameTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String city = cityTextField.getText().trim();
        String country = countryTextField.getText().trim();
        String zipCode = postalCodeTextField.getText().trim();
        Integer active = activeCb.isSelected()?1:0;

        if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || cityTextField.getText().isEmpty() || postalCodeTextField.getText().isEmpty() ||  phoneTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter data in all fields!");
            alert.showAndWait();
        }else {
            //Country Table
            //Integer countryId = this.customer.getCountryId();
            Integer countryId = DBCountry.getCountryId(this.customer.getCustomerCountry());


            if(!(country.equals(customer.getCountry()))){
                //countryId = DBCountry.getCountryId(country);
                if (countryId == null)
                    countryId = DBCountry.addToCountryTable(country);
            }

            //City Table

            Integer cityId = DBCity.getCityIdFromAddressId(this.customer.getAddressId());


            if(!(city.equals(this.customer.getCustomerCity()))){
                //cityId = DBCity.getCityId(city, countryId);
                if (cityId == null){
                    cityId = DBCity.addToCityTable(city, countryId);}
                else{
                    cityId = DBCity.updateToCityTable(city, countryId, cityId);

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

    public void populateCustomerFields(Customer selectedCustomer){
        this.customer = selectedCustomer;
        countryTextField.setEditable(false);
        countryTextField.setDisable(true);
        activeCb.setDisable(true);

        nameTextField.setText(selectedCustomer.getCustomerName());
        phoneTextField.setText(selectedCustomer.getCustomerPhone());
        addressTextField.setText((selectedCustomer.getCustomerAddress()));
        postalCodeTextField.setText(selectedCustomer.getCustomerPostalCode());
        cityTextField.setText(selectedCustomer.getCustomerCity());
        countryTextField.setText(selectedCustomer.getCustomerCountry());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
