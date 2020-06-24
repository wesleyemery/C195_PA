package View_Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class updateAppointmentController {
    @FXML
    private Label userNameLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField appointmentTitleTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField appointmentTypeTextField;

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
    private ComboBox<?> customerComboBox;

    @FXML
    private DatePicker datePicker1;


}


