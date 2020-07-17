package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class updateAppointmentController {

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<?> startCb;

    @FXML
    private ComboBox<?> endCb;

    @FXML
    private Label customerNameLabel;

    @FXML
    private ComboBox<?> customerCb;

    @FXML
    private DatePicker startDate;

    @FXML
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;

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

    @FXML
    void saveAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void startDateAction(ActionEvent event) {

    }



}


