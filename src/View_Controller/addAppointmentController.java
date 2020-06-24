package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {
    @FXML
    private Label userNameLabel;

    @FXML
    private TextField appointmentTitleTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField appointmentTypeTextField;

    @FXML
    private TextArea appointmentDescriptionTextArea;

    @FXML
    private TextField startHourTextField;

    @FXML
    private TextField startMinuteTextField;

    @FXML
    private ChoiceBox<?> startAmPmChoiceBox;

    @FXML
    private TextField endHourTextField;

    @FXML
    private TextField endMinuteTextField;

    @FXML
    private ChoiceBox<?> endAmPmChoiceBox;

    @FXML
    private Label customerNameLabel;

    @FXML
    private ChoiceBox<?> customerChoiceBox;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelHandler(ActionEvent event) {

    }

    @FXML
    void saveAppointmentHandler(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
