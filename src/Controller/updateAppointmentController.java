package Controller;

import Database.DBAppointment;
import Database.DBConnection;
import Database.DBCustomer;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


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
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;
    ObservableList<String> customerNames = FXCollections.observableArrayList();
    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> hoursArray = FXCollections.observableArrayList();
    Appointment appointment;
    LocalDate startDate1;
    LocalTime startTime;
    LocalDate endDate1;
    LocalTime endTime;

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
    private void startDateTimeSetter() {
        LocalDateTime startDateTime = appointment.getStart();
        String startDateTimeStr = startDateTime.toString();
        if (startDateTimeStr.isEmpty())
            System.out.println("The StartDateTime variable is empty: " + startDateTime);
        else {
            startDate1 = LocalDate.parse(startDateTimeStr.substring(0, startDateTimeStr.indexOf('T')));
            startTime = LocalTime.parse(startDateTimeStr.substring(11, 16), DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    private void endDateTimeSetter() {
        LocalDateTime endDateTime = appointment.getEnd();
        String endDateTimeStr = endDateTime.toString();
        if (endDateTimeStr.isEmpty())
            System.out.println("The StartDateTime variable is empty: " + endDateTime);
        else {
            endDate1 = LocalDate.parse(endDateTimeStr.substring(0, endDateTimeStr.indexOf('T')));
            endTime = LocalTime.parse(endDateTimeStr.substring(11, 16), DateTimeFormatter.ofPattern("HH:mm"));
        }
    }
    public void setCustomerNames() {
        for (Customer c:customerArray) {
            customerNames.add(c.getCustomerName());
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


    @FXML
    void saveAppointmentHandler(ActionEvent event) {
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
        } else if(customer.isEmpty() || customer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter customer data!");
            alert.showAndWait();
        }else if (startCb.getValue() == null || endCb.getValue() == null || startDate.getValue() == null || endDate.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter time and date data!");
            alert.showAndWait();
        } else if (end != null && start != null) {
            if (end.isBefore(start) || end.equals(start)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("End Time cannot be at the same time or before the Start Time.");
                alert.showAndWait();
            }
        } else {
            String startStr = start.toString();
            String endStr = end.toString();
            DBAppointment.updateToAppointmentTable(appointment.getCustomerId(), title, startStr, endStr, type, appointment.getAppointmentId());
            backToMain(event);
        }
    }

    @FXML
    void startDateAction(ActionEvent event) {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //customerArray.clear();
        queryAllCustomerNames();
        setCustomerNames();
        /*ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer c:customerArray) {
            customerNames.add(c.getCustomerName());
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

    }
}


