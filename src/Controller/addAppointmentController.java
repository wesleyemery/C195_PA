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
import java.util.Optional;
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
    private ComboBox<String> customerComboBox;

    @FXML
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;
    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
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
        String date = datePicker.getValue().toString();
        String startHour = startHourTextField.getText().trim();
        String startMinute = startMinuteTextField.getText().trim();
        String endHour = endHourTextField.getText().trim();
        String endMinute = endMinuteTextField.getText().trim();
        String type = appointmentTypeTextField.getText().trim();
        String description = appointmentDescriptionTextArea.getText().trim();


        if (appointmentTitleTextField.getText().isEmpty() || startHourTextField.getText().isEmpty() || startMinuteTextField.getText().isEmpty() || endHourTextField.getText().isEmpty() ||  endMinuteTextField.getText().isEmpty() || appointmentTypeTextField.getText().isEmpty() || appointmentDescriptionTextArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter data in all fields!");
            alert.showAndWait();
        }else {


        }

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
    }
}
