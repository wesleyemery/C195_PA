package View_Controller;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Utils.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentStartColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerColumn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    @FXML
    private TableColumn<Customer, String> customerCountryColumn;

    @FXML
    private TableColumn<Customer, String> customerCityColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    private Button newCustomerBtn;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    void deleteAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void deleteCustomerHandler(ActionEvent event) {

    }

    @FXML
    void newAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void newCustomerHandler(ActionEvent event) {

    }

    @FXML
    void updateAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void updateCustomerHandler(ActionEvent event) {

    }

    public void generateCustomerTable(){
        customerTable.getItems().setAll(queryCustomers());
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        /*appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));*/
    }

    public static ObservableList<Customer> queryCustomers() {
        String query = "SELECT customer.customerId, customer.customerName, customer.addressId, address.address, address.postalCode, city.cityId, city.city, country.country, address.phone "
                + "FROM customer, address, city, country "
                + "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId;";
        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(query);
            //getInstance().connection().createStatement().executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("customer.customerId");
                String name = rs.getString("customer.customerName");
                String address = rs.getString("address.address");
                int addressId = rs.getInt("customer.addressId");
                String city = rs.getString("city.city");
                String country = rs.getString("country.country");
                String phone = rs.getString("address.phone");
                allCustomers.add(new Customer(id, name, addressId, address, city, country, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateCustomerTable();
    }
}
