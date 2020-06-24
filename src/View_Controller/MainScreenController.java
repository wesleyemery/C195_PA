package View_Controller;

import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
