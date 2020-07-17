package Controller;

import Database.DBAppointment;
import Database.DBConnection;
import Database.DBCustomer;
import Database.DBQuery;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;

    Customer customer = new Customer();
    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> hoursArray = FXCollections.observableArrayList();
    
    @FXML
    void startDateAction(ActionEvent event) {
        endDate.setValue(startDate.getValue());
    }
    
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
        }else if (startCb.getValue() == null || endCb.getValue() == null || startDate.getValue() == null || endDate.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter time and date data!");
            alert.showAndWait();
        }else {

            String name = customerCb.getValue();
            String startTime = getStartDateTime();
            String endTime = getEndDateTime();
            //addToAppointmentTable(Integer customerId, String title, String startTime, String endTime, String type,  String description)
            DBAppointment.addToAppointmentTable(DBCustomer.getCustomerId(name), title, startTime, endTime, type, description);
            backToMain(event);
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
        hoursArray.add(6, LocalTime.parse("12:00"));
        hoursArray.add(7, LocalTime.parse("12:30"));
        hoursArray.add(8, LocalTime.parse("13:00"));
        hoursArray.add(9, LocalTime.parse("13:30"));
        hoursArray.add(10, LocalTime.parse("14:00"));
        hoursArray.add(11, LocalTime.parse("14:30"));
        hoursArray.add(12, LocalTime.parse("15:00"));
        hoursArray.add(13, LocalTime.parse("15:30"));

        return hoursArray;
    }

    public String getStartDateTime() {
        ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = startDate.getValue().toString() + "T" + startCb.getValue().toString() + ":00" + oS + "[" + zoneId + "]";
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
        String localDateTime = endDate.getValue().toString() +"T" + endCb.getValue().toString() + ":00" + oS + "[" + zoneId + "]";
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
       /* hoursArray.clear();
        hoursArray.add(0, LocalTime.parse("09:00"));
        hoursArray.add(1, LocalTime.parse("09:30"));
        hoursArray.add(2, LocalTime.parse("10:00"));
        hoursArray.add(3, LocalTime.parse("10:30"));
        hoursArray.add(4, LocalTime.parse("11:00"));
        hoursArray.add(5, LocalTime.parse("11:30"));
        hoursArray.add(6, LocalTime.parse("14:00"));
        hoursArray.add(7, LocalTime.parse("14:30"));
        hoursArray.add(8, LocalTime.parse("15:00"));
        hoursArray.add(9, LocalTime.parse("15:30"));*/
        /*for (LocalTime time:hoursArray) {
            System.out.println(time);

        }*/

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

        setHours();
        startCb.setItems(hoursArray);
        endCb.setItems(hoursArray);

        /*ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneOffset oS = ZoneId.of(zoneId.toString()).getRules().getOffset(Instant.now());
        String localDateTime = startDate.getValue().toString() + "T" + startCb.getValue().toString() + ":00" + oS + "[" + zoneId + "]";*/
    }
}
