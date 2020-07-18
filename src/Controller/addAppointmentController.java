package Controller;

import Database.*;
import Model.Appointment;
import Model.Customer;
import Model.User;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import Controller.loginController;

public class addAppointmentController implements Initializable {
    @FXML
    private Label userNameLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    public DatePicker startDate;

    @FXML
    public DatePicker endDate;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<LocalTime> startCb;

    @FXML
    private ComboBox<LocalTime> endCb;

    @FXML
    private Label customerNameLabel;


    @FXML
    private ComboBox<String> customerCb;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private Button cancelButton;

    Customer customer = new Customer();
    Appointment appointment;
    ObservableList<String> customerNames = FXCollections.observableArrayList();

    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> start = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> end = FXCollections.observableArrayList();

    @FXML
    void startDateAction(ActionEvent event) {
        endDate.setValue(startDate.getValue());
    }

    public void setCustomerNames() {
        for (Customer c:customerArray) {
            customerNames.add(c.getCustomerName());
        }
    }
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
    void saveAppointmentAction(ActionEvent event) {

        String title = titleTextField.getText();
        LocalDate startDateValue = startDate.getValue();
        LocalDate endDateValue = endDate.getValue();
        LocalTime start = startCb.getValue();
        LocalTime end = endCb.getValue();
        String type = typeTextField.getText().trim();
        String description = descriptionTextArea.getText().trim();


        if (titleTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || descriptionTextArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter data in all fields!");
            alert.showAndWait();
        }
        if (startCb.getValue() == null || endCb.getValue() == null || startDate.getValue() == null || endDate.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter time and date data!");
            alert.showAndWait();
        }
        if (end != null && start != null) {
            if ( end.equals(start)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("End Time cannot be at the same time or before the Start Time.");
                alert.showAndWait();
            }
        }

        Integer id = DBUser.queryUserId();
        if (id != null){
            String st = getStartDateTime() + ":00";
            String et = getEndDateTime() + ":00";
            if (DBAppointment.isOverlap(st, et, id)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setContentText("This appointment overlaps with another");
                alert.showAndWait();
                return;
            }
        }
        String name = customerCb.getValue();
        String startTime = getStartDateTime();
        String endTime = getEndDateTime();
        //addToAppointmentTable(Integer customerId, String title, String startTime, String endTime, String type,  String description)
        DBAppointment.addToAppointmentTable(DBCustomer.getCustomerId(name), title, startTime, endTime, type, description);
        backToMain(event);

    }

    public String getStartDateTime() {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = startDate.getValue().toString() + "T" + startCb.getValue() + ":00" + oS + "[" + zoneId + "]";
        ZonedDateTime dateTime = ZonedDateTime.parse(localDateTime);
        Instant localUtcInstant = dateTime.toInstant();
        ZonedDateTime utcDateTime = localUtcInstant.atZone(ZoneOffset.UTC);
        String date, time;
        date = String.valueOf(utcDateTime.toLocalDate());
        time = String.valueOf(utcDateTime.toLocalTime());
        String dateTimeString = date + " " + time;
        return dateTimeString;
    }

    public String getEndDateTime() {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = endDate.getValue().toString() +"T" + endCb.getValue() + ":00" + oS + "[" + zoneId + "]";
        ZonedDateTime dateTime = ZonedDateTime.parse(localDateTime);
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
                String customerName = rs.getString("customerName");
                customerArray.add(new Customer(customerName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerArray;
    }
    public void setTimes() {
        LocalTime time = LocalTime.of(8, 0);
        do {
            start.add(time);
            end.add(time);
            time = time.plusMinutes(15);
        } while (!time.equals(LocalTime.of(17, 15)));
        start.remove(start.size() - 1);
        end.remove(0);
        startCb.setItems(start);
        endCb.setItems(end);
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
        setCustomerNames();

        //Lambda to restrict access to dates outside business hours
        startDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable( empty
                        || date.isBefore(LocalDate.now())
                        || date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                        || date.getDayOfWeek().equals(DayOfWeek.SUNDAY));
                if( date.isBefore(LocalDate.now())
                        || date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                        || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        endDate.setDisable(true);
        customerCb.setItems(customerNames);
        setTimes();

    }
}
