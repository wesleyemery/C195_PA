package View_Controller;

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
import java.util.Optional;
import java.util.ResourceBundle;

public class updateCustomerController implements Initializable {
    @FXML
    private Label customerNameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<?> cityComboBox;

    @FXML
    private TextField phoneField;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button updateCustomerBtn;

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
    void updateCustomerHandler(ActionEvent event) {

    }
    private void backToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            MainScreenController controller = new MainScreenController();
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

    }
}
