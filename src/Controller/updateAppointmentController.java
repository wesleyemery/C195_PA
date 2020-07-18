package Controller;

import Database.DBAppointment;
import Database.DBConnection;
import Database.DBCustomer;
import Database.DBUser;
import Model.Appointment;
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
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class updateAppointmentController implements Initializable {
    @FXML
    private Label userNameLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<LocalTime> startCb;

    @FXML
    private ComboBox<LocalTime> endCb;

    @FXML
    private Label customerNameLabel;

    @FXML
    private ComboBox<String> customerCb;

    @FXML
    private DatePicker startDate;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private Button cancelButton;
    ObservableList<String> customerNames = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> start = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> end = FXCollections.observableArrayList();
    Appointment appointment;
    LocalDate startDate1, endDate1;
    LocalTime startTime, endTime;



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
    public void populateAppointmentFields(Appointment appointment) {

        this.appointment = appointment;
        startDateTimeSetter();
        endDateTimeSetter();
        titleTextField.setText(appointment.getTitle());
        typeTextField.setText(appointment.getTitle());
        String customerName = DBCustomer.getCustomerName(appointment.getCustomerId());
        customerCb.setValue(customerName);
        startDate.setValue(startDate1);
        startCb.setValue(startTime);
        endDate.setValue(endDate1);
        endCb.setValue(endTime);

    }
    private void startDateTimeSetter() {
        String startDateTime = appointment.getStart();
        if (startDateTime.isEmpty())
            System.out.println("The StartDateTime variable is empty: " + startDateTime);
        else {
            startDate1 = LocalDate.parse(startDateTime.substring(0, startDateTime.indexOf(' ')));
            startTime = LocalTime.parse(startDateTime.substring(11, 16), DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    private void endDateTimeSetter() {
        String endDateTime = appointment.getEnd();
        if (endDateTime.isEmpty())
            System.out.println("The StartDateTime variable is empty: " + endDateTime);
        else {
            endDate1 = LocalDate.parse(endDateTime.substring(0, endDateTime.indexOf(' ')));
            endTime = LocalTime.parse(endDateTime.substring(11, 16), DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    public String getStartDateTime() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = startDate.getValue() + "T" + startCb.getValue() + ":00" + oS + "[" + zoneId + "]";

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
        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = endDate.getValue() + "T" + endCb.getValue() + ":00" + oS + "[" + zoneId + "]";

        ZonedDateTime dateTime = ZonedDateTime.parse(localDateTime);
        Instant localToUtcInstant = dateTime.toInstant();
        ZonedDateTime utcDateTime = localToUtcInstant.atZone(ZoneOffset.UTC);
        String date, time;
        date = String.valueOf(utcDateTime.toLocalDate());
        time = String.valueOf(utcDateTime.toLocalTime());
        String dateTimeString = date + " " + time;
        return dateTimeString;
    }
    public void setCustomerNames() {
        for (Customer c:customerArray) {
            customerNames.add(c.getCustomerName());
        }
    }

    @FXML
    void startDateAction(ActionEvent event) {
        endDate.setValue(startDate.getValue());
    }

    @FXML
    void saveAppointmentAction(ActionEvent event) {
        String title = titleTextField.getText();
        String type = typeTextField.getText();
        String customer = customerCb.getValue();
        LocalDate startDate1 = startDate.getValue();
        LocalTime start = startCb.getSelectionModel().getSelectedItem();
        LocalDate endDate1 = endDate.getValue();
        LocalTime end = endCb.getSelectionModel().getSelectedItem();

        if (typeTextField.getText().isEmpty() || titleTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter data in all fields!");
            alert.showAndWait();
        }
        if (customer.isEmpty() || customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter customer data!");
            alert.showAndWait();
        }
        if (startCb.getValue() == null || endCb.getValue() == null || startDate.getValue() == null || endDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter time and date data!");
            alert.showAndWait();
        }
        if (end != null && start != null) {
            if (end.isBefore(start) ||  end.equals(start)) {
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

        int custId = appointment.getCustomerId();
        int apptId = appointment.getAppointmentId();
        String startTime = getStartDateTime();
        String endTime = getEndDateTime();
        DBAppointment.updateToAppointmentTable(custId, title, startTime, endTime, type, apptId);
        backToMain(event);

    }



    public static ObservableList<Customer> queryAllCustomerNames(){
        String query = "SELECT customerId, customerName FROM customer;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer customerId = rs.getInt("customer.customerId");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        queryAllCustomerNames();
        setCustomerNames();

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


