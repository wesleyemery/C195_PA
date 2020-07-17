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
    static User currentUser;
    private String errorPassword, errorBlank, errorTitle, exitMessage, exitHeader, loginTitle, loginHeader;

    @FXML
    void exitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Alert: Exiting the Scheduling System");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK){
            Platform.exit();
        }

    }

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        boolean userNameValid = false, passwordValid = false;

        //validation
        if (userName.isEmpty() || userName==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText("Error");
            alert.setContentText(errorBlank);
            alert.show();
            userNameValid = false;

        } else if (password.isEmpty() || password==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText("Error");
            alert.setContentText(errorBlank);
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
                        break;
                    }
                }

            }


            if (userNameValid && passwordValid) {
                    /*User inputUser = new User(userName, password);
                    currentUser = existingUser(inputUser);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(loginTitle);
                    alert.setHeaderText(loginHeader);
                    alert.showAndWait();*/
                    // Sets current user for the current session

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
                alert.setTitle(errorTitle);
                alert.setHeaderText("Error");
                alert.setContentText("Username is invalid");
                alert.show();
            }else if(userNameValid && passwordValid == false){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(errorTitle);
                alert.setHeaderText("Error");
                alert.setContentText("Password is invalid");
                alert.show();
            }

            }
        }


    public void textFieldGenerate(){
        userNameTextField.setText(rb.getString("user"));
        passwordTextField.setText(rb.getString("password"));
    }

    User existingUser(User loginAttempt) {
        User currentUser = new User();
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE userName=? AND password=?");
            ps.setString(1, loginAttempt.getUserName());
            ps.setString(2, loginAttempt.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //int userId = results.getInt("userId");
                //System.out.println(ps.getUpdateCount() + " user found.");
                currentUser.setUserId(rs.getInt("userId"));
                currentUser.setUserName(rs.getString("userName"));
                currentUser.setPassword(rs.getString("password"));
            } else { // user not found
                System.out.println("Not found.");
                return null;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("Languages/language", locale.getDefault());
        textFieldGenerate();
        if (locale.getDefault().getLanguage().equals("es") ||  locale.getDefault().getLanguage().equals("en")) {
            errorPassword = rb.getString("errorPassword");
            errorBlank = rb.getString("errorBlank");
            errorTitle = rb.getString("errorTitle");
            exitMessage = rb.getString("exitMessage");
            exitHeader = rb.getString("exitHeader");
            userNameLabel.setText(rb.getString("user"));
            passwordLabel.setText(rb.getString("password"));
            loginButton.setText(rb.getString("login"));
            loginTitle = rb.getString("loginTitle");
            loginHeader = rb.getString("loginHeader");

        }

    }

}
