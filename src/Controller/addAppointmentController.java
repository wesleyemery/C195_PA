package Controller;

import Database.DBConnection;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class addAppointmentController implements Initializable {
    @FXML
    private Label userNameLabel;

    @FXML
    private TextField appointmentTitleTextField;

    @FXML
    public static DatePicker startDatePicker;

    @FXML
    public static DatePicker endDatePicker;

    @FXML
    private TextField appointmentTypeTextField;

    @FXML
    private TextArea appointmentDescriptionTextArea;

    @FXML
    public static ComboBox<LocalTime> startCb;

    @FXML
    public static ComboBox<LocalTime> endCb;

    @FXML
    private Label customerNameLabel;


    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;
    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> hoursArray = FXCollections.observableArrayList();

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
    void saveAppointmentHandler(ActionEvent event) {

        String title = appointmentTitleTextField.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime start = startCb.getValue();
        LocalTime end = endCb.getValue();
        String type = appointmentTypeTextField.getText().trim();
        String description = appointmentDescriptionTextArea.getText().trim();


        if (appointmentTitleTextField.getText().isEmpty() || appointmentTypeTextField.getText().isEmpty() || appointmentDescriptionTextArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter data in all fields!");
            alert.showAndWait();
        }else if (start == null || end == null || startDate == null || endDate == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter time and date data!");
            alert.showAndWait();
        }else {

        }

    }

    public static ObservableList<LocalTime> setHours() {
        hoursArray.clear();
        hoursArray.add(0, LocalTime.parse("09:00"));
        hoursArray.add(1, LocalTime.parse("09:30"));
        hoursArray.add(2, LocalTime.parse("10:00"));
        hoursArray.add(3, LocalTime.parse("10:30"));
        hoursArray.add(4, LocalTime.parse("11:00"));
        hoursArray.add(5, LocalTime.parse("11:30"));
        hoursArray.add(6, LocalTime.parse("14:00"));
        hoursArray.add(7, LocalTime.parse("14:30"));
        hoursArray.add(8, LocalTime.parse("15:00"));
        hoursArray.add(9, LocalTime.parse("15:30"));

        return hoursArray;
    }

    public static String getStartDateTime() {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = startDatePicker.getValue().toString() + "T" + startCb.getValue().toString() + ":00" + oS + "[" + zoneId + "]";
        ZonedDateTime dateTime = ZonedDateTime.parse(localDateTime);
        Instant localUtcInstant = dateTime.toInstant();
        ZonedDateTime utcDateTime = localUtcInstant.atZone(ZoneOffset.UTC);
        String date, time;
        date = String.valueOf(utcDateTime.toLocalDate());
        time = String.valueOf(utcDateTime.toLocalTime());
        String dateTimeString = date + " " + time;
        return dateTimeString;
    }

    public static String getEndDateTime() {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTimeString = endDatePicker.getValue().toString() +"T" + endCb.getValue().toString() + ":00" + oS + "[" + zoneId + "]";
        ZonedDateTime dateTime = ZonedDateTime.parse(localDateTimeString);
        Instant localToUtcInstant = dateTime.toInstant();
        ZonedDateTime utcDateTime = localToUtcInstant.atZone(ZoneOffset.UTC);
        String date, time;
        date = String.valueOf(utcDateTime.toLocalDate());
        time = String.valueOf(utcDateTime.toLocalTime());
        String dateTimeString = date + " " + time;
        return dateTimeString;
    }

    public static ObservableList<Customer> queryAllCustomerNames(){
        String query = "SELECT customerName FROM customer;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                //Integer customerId = rs.getInt("customer.customerId");
                String customerName = rs.getString("customerName");
                customerArray.add(new Customer(customerName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerArray;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerArray.clear();
        queryAllCustomerNames();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer c:customerArray) {
            customerNames.add(c.getCustomerName());
        }
        customerComboBox.setItems(customerNames);
        startCb.setItems(hoursArray);
        endCb.setItems(hoursArray);
    }
}
