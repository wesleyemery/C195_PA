package Controller;

import Database.DBConnection;
import Database.DBCustomer;
import Database.DBUser;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class scheduleReportController implements Initializable {

    @FXML
    private TextArea scheduleTextArea;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> userCB;

    @FXML
    void selectUserAction(ActionEvent event) {
        String user = userCB.getValue();
        int id = DBUser.queryUserIdfromName(user);
        getScheduleReport(id);
    }

    private int getID(){
        String user = (String)userCB.getValue();
        int id = DBUser.queryUserIdfromName(user);
        return id;
    }

    private void populateUser() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        ObservableList<String> userNames = FXCollections.observableArrayList();
        allUsers = DBUser.queryAllUserNames();
        for (User u:allUsers) {
            userNames.add(u.getUserName());
        }
        userCB.setItems(userNames);
        userCB.setValue(userNames.get(0));
    }
    @FXML
    void backButtonAction(ActionEvent event) {
        backToMain(event);
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

    public void getScheduleReport(int id) {

        try {
            Statement sm = DBConnection.getConnection().createStatement();
            String query = "SELECT title, start, end FROM appointment WHERE userId = '" + id + "';";
            ResultSet rs = sm.executeQuery(query);
            StringBuilder sb = new StringBuilder();
            sb.append("Schedule: \n\n");
            sb.append(String.format("%1$-45s %2$-45s %3$-45s \n", "Title", "Start    ", "End    "));
            sb.append("\n");
            while (rs.next()) {
                sb.append(String.format("%1$-30s %2$-35s %3$-35s \n", rs.getString("title"), rs.getString("start"), rs.getString("end")));}
            sm.close();
            scheduleTextArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateUser();
        String user = (String)userCB.getValue();
        int id = DBUser.queryUserIdfromName(user);
        getScheduleReport(id);
    }
}
