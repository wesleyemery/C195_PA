package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Model.User;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class  LoginController<user, currentUser> implements Initializable {

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label lblLanguage;

    @FXML
    private ImageView flagIcon;

    static User currentUser;
    private String errorText, errorText1, errorHeader, exitMessage, exitHeader;

    @FXML
    void login(ActionEvent event) {
        String userNameInput = userNameTextField.getText();
        String passwordInput = passwordField.getText();

        //validation
        /*if (userNameInput.isEmpty() || passwordInput.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorHeader);
            alert.setHeaderText("Error");
            alert.setContentText(errorText1);
            alert.showAndWait();
        } else {
            currentUser = existingUser(userNameInput, passwordInput);
            if (currentUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(errorHeader);
                alert.setHeaderText("Error");
                alert.setContentText(errorText);
                alert.showAndWait();
            } else {
                //log successful login
                LoginLogger.log(userNameInput);
                //go to main screen after successful login
                Stage stage;
                Parent root;
                stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }*/

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Languages/language", locale.getDefault());
        if (locale.getDefault().getLanguage().equals("es") ||  locale.getDefault().getLanguage().equals("en"))
            System.out.println(rb.getString("HI"));


    }

}
