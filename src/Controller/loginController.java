package Controller;

import Database.DBConnection;
import Database.DBUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class loginController implements Initializable {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;


    private ResourceBundle rb;
    Locale locale;
    static User currentUser = new User();
    private String passwordErr, missingInput, titleErr, exitAlert, exitHeader, loginTitle, loginHeader, confirm;

    @FXML
    void exitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(confirm);
        alert.setHeaderText(exitHeader);
        alert.setContentText(exitAlert);

        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK){
            Platform.exit();
        }

    }

    private void setResourceBundle() {
        passwordErr = rb.getString("passwordErr");
        missingInput = rb.getString("missingInput");
        titleErr = rb.getString("titleErr");
        exitAlert = rb.getString("exitAlert");
        exitHeader = rb.getString("exitHeader");
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        loginTitle = rb.getString("loginTitle");
        loginHeader = rb.getString("loginHeader");
        confirm = rb.getString("confirm");
    }

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        boolean userNameValid = false, passwordValid = false;

        //validation
        if (userName.isEmpty() || userName==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titleErr);
            alert.setHeaderText(titleErr);
            alert.setContentText(missingInput);
            alert.show();
            userNameValid = false;

        } else if (password.isEmpty() || password==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titleErr);
            alert.setHeaderText(titleErr);
            alert.setContentText(missingInput);
            alert.show();
            passwordValid = false;
        } else{
            ObservableList<User> allUsers =  FXCollections.observableArrayList();
            allUsers = DBUser.queryUsers();
            for (User u:allUsers) {
                if(u.getUserName().equals(userName)){
                    userNameValid = true;
                    if(u.getPassword().equals(password)){
                        passwordValid = true;
                        currentUser = u;
                        break;
                    }
                }

            }
            if (userNameValid && passwordValid) {
                loginLogger(currentUser);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/mainScreen.fxml"));
                mainScreenController controller = new mainScreenController();
                Stage stage = (Stage) loginButton.getScene().getWindow();

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                }
            else if(userNameValid == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(titleErr);
                alert.setHeaderText(titleErr);
                alert.setContentText(missingInput);
                alert.show();
            }else if(userNameValid && passwordValid == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(titleErr);
                alert.setHeaderText(titleErr);
                alert.setContentText(missingInput);
                alert.show();
            }

            }
        }


    public void textFieldGenerate(){
        userNameTextField.setText(rb.getString("username"));
        passwordTextField.setText(rb.getString("password"));
    }


    public User getCurrentUser() {
        return this.currentUser;
    }

    // similar method found in C195LoggerExample
    private void loginLogger(User user) {
        Logger log = Logger.getLogger("loginLogger.txt");
        log.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler("loginLogger.txt", true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            log.addHandler(fileHandler);
        } catch (Exception e) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, e);
        }
        if (user != null) {
            log.log(Level.INFO, "User " + user.getUserName() + " successfully logged in.");
        } else {
            log.log(Level.INFO, "Invalid UserName or Password.");
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("Languages/language", locale.getDefault());
        textFieldGenerate();
        if (locale.getDefault().getLanguage().equals("es") ||  locale.getDefault().getLanguage().equals("en") || locale.getDefault().getLanguage().equals("mx"))
            setResourceBundle();


    }

}
