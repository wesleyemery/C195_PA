package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML
    private TableView<?> resultsTable;

    @FXML
    private ComboBox<String> reportsCb;

    @FXML
    private Button executeButton;

    @FXML
    void executeButtonAction(ActionEvent event) {

    }

    @FXML
    void reportsCbAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
