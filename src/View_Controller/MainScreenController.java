package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class MainScreenController {

    @FXML
    private TableColumn<?, ?> appointmentIDColumn;

    @FXML
    private TableColumn<?, ?> appointmentTitleColumn;

    @FXML
    private TableColumn<?, ?> appointmentTypeColumn;

    @FXML
    private TableColumn<?, ?> appointmentStartColumn;

    @FXML
    private TableColumn<?, ?> appointmentEndColumn;

    @FXML
    private TableColumn<?, ?> appointmentCustomerColumn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TableColumn<?, ?> customerIDColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> customerAddressColumn;

    @FXML
    private TableColumn<?, ?> customerCountryColumn;

    @FXML
    private TableColumn<?, ?> customerCityColumn;

    @FXML
    private TableColumn<?, ?> customerPhoneColumn;

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

}
