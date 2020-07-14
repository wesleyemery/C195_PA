package Controller;

import Database.DBConnection;
import Model.Appointment;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {



    private static ObservableList<Customer> customerArray = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
    User user;

    @FXML
    private ComboBox<String> cbReport;

    @FXML
    private Button btnExecute;

    @FXML
    void btnExecuteAction(ActionEvent event) {

    }

    @FXML
    void cbReportAction(ActionEvent event) {

    }
    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentStartColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerColumn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    @FXML
    private TableColumn<Customer, String> customerCountryColumn;

    @FXML
    private TableColumn<Customer, String> customerCityColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    @FXML
    private Button newCustomerBtn;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private RadioButton radioWeek;

    @FXML
    private ToggleGroup dateSelect;

    @FXML
    private RadioButton radioMonth;

    @FXML
    private RadioButton radioAll;

    @FXML
    void deleteAppointmentHandler(ActionEvent event) {
        Appointment appointmentDelete = appointmentTable.getSelectionModel().getSelectedItem();
        if(appointmentDelete != null) {
            String message = "Are you sure you want to delete " + appointmentTable.getSelectionModel().getSelectedItem().getTitle() + " appointment?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ALERT: Delete Selected");
            alert.setHeaderText("Confirm");
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    PreparedStatement ps = DBConnection.getConnection().prepareStatement("DELETE FROM appointment WHERE appointmentId = ?");
                    ps.setInt(1, appointmentDelete.getAppointmentId());
                    ps.execute();

                    appointmentArray.clear();
                    appointmentTable.setItems(queryAppointments());
                    appointmentTable.refresh();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please select appointment");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void deleteCustomerHandler(ActionEvent event) {
        Customer customerDelete = customerTable.getSelectionModel().getSelectedItem();
        if(customerDelete != null) {
            String message = "Are you sure you want to delete " + customerTable.getSelectionModel().getSelectedItem().getCustomerName() + "?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ALERT: Delete Selected");
            alert.setHeaderText("Confirm");
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    PreparedStatement ps = DBConnection.getConnection().prepareStatement("DELETE FROM appointment WHERE customerId = ?");
                    ps.setInt(1, customerDelete.getCustomerId());
                    ps.execute();
                    ps = DBConnection.getConnection().prepareStatement("DELETE FROM customer WHERE customerId = ?");
                    ps.setInt(1, customerDelete.getCustomerId());
                    ps.execute();

                    customerArray.clear();
                    customerTable.setItems(queryCustomers());
                    customerTable.refresh();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please select Customer");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void newAppointmentHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addAppointmentScreen.fxml"));
        addAppointmentController controller = new addAppointmentController();
        Stage stage = (Stage) newAppointmentBtn.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void newCustomerHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addCustomerScreen.fxml"));
        addCustomerController controller = new addCustomerController();
        Stage stage = (Stage) newCustomerBtn.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void updateAppointmentHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/updateAppointmentScreen.fxml"));
        updateAppointmentController controller = new updateAppointmentController();
        Stage stage = (Stage) updateAppointmentBtn.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        //Address address = address.getAddress();
        if (!(selectedCustomer == null)){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/updateCustomerScreen.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                updateCustomerController controller = loader.getController();
                controller.populateCustomerFields(selectedCustomer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            return;

       /* updateCustomerController controller = new updateCustomerController();
        controller.populateCustomerFields(selectedCustomer);

        Stage stage = (Stage) updateAppointmentBtn.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/

    }


    public void appointmentTime15() {


        LocalDateTime localDateTime =Utils.Time.getLocalDateTime();
        LocalDateTime localDateTimeAdd15 = Utils.Time.getLocalDateTimeAdd15();

        String query = "SELECT * FROM appointment WHERE userId = "+ user.getUserId() +" AND start BETWEEN '" + localDateTime + "' AND '" + localDateTimeAdd15 + "';";
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("You have an up coming appointment.");
                alert.setContentText("You have an appointment in 15 minutes.");
                alert.showAndWait();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void allAction(ActionEvent event) {
        generateAppointmentTable();

    }

    @FXML
    void monthAction(ActionEvent event) {
        generateAppointmentTable();

    }

    @FXML
    void weekAction(ActionEvent event) {
        generateAppointmentTable();

    }
    public void generateAppointmentTable(){
        appointmentTable.getItems().setAll(queryAppointments());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

        public void generateCustomerTable(){
        customerTable.getItems().setAll(queryCustomers());
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

    }
    public ObservableList<Appointment> queryAppointments() {

        if (radioAll.isSelected()) {
            appointmentArray.clear();
            String query = "SELECT appointmentId, customerId, title, type, start, end FROM appointment";

            try {
                ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(query);
                while (rs.next()) {
                    int id = rs.getInt("appointmentID");
                    int customerId = rs.getInt("customerID");
                    String title = rs.getString("title");
                    String type = rs.getString("type");
                    LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();
                    appointmentArray.add(new Appointment(id, customerId, title, type, start, end));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return appointmentArray;
        } else if(radioMonth.isSelected()){
            appointmentArray.clear();
            String query = "SELECT appointmentId, customerId, title, type, start, end FROM appointment WHERE appointment.start >= '" + Utils.Time.getMonth() + "' AND appointment.end <= '" + Utils.Time.getEndOfMonth() + "' AND userId='1';";
            System.out.println(query);
            try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(query);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("appointment.appointmentId");
                    int customerId = rs.getInt("appointment.customerId");
                    String title = rs.getString("appointment.title");
                    String type = rs.getString("appointment.type");
                    String start = rs.getString("appointment.start");
                    String timeStart = Utils.Time.getStartDateTime(start);
                    String end = rs.getString("appointment.end");
                    String timeEnd = Utils.Time.getEndDateTime(end);
                    appointmentArray.add(new Appointment(id, customerId, title, type, timeStart, timeEnd));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return appointmentArray;
        } else if(radioWeek.isSelected()) {
            appointmentArray.clear();
            String query = "SELECT appointmentId, customerId, title, type, start, end FROM appointment WHERE appointment.start >= '" + Utils.Time.getWeek() + "' AND appointment.end <= '" + Utils.Time.getWeekLater() + "' AND userId='1';";
            //System.out.println(query);
            try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(query);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("appointment.appointmentId");
                    int customerId = rs.getInt("appointment.customerId");
                    String title = rs.getString("appointment.title");
                    String type = rs.getString("appointment.type");
                    String start = rs.getString("appointment.start");
                    String timeStart = Utils.Time.getStartDateTime(start);
                    String end = rs.getString("appointment.end");
                    String timeEnd = Utils.Time.getEndDateTime(end);
                    appointmentArray.add(new Appointment(id, customerId, title, type, timeStart, timeEnd));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return appointmentArray;
        }
        else {
            return appointmentArray;
        }
    }
        public static ObservableList<Customer> queryCustomers() {
        String query = "SELECT customer.customerId, customer.customerName, address.addressId, address.address, address.postalCode, city.cityId, city.city, country.country, address.phone "
                + "FROM customer, address, city, country "
                + "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId;";
        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(query);
            //getInstance().connection().createStatement().executeQuery(query);
            while (rs.next()) {
                Integer id = rs.getInt("customer.customerId");
                String name = rs.getString("customer.customerName");
                String address = rs.getString("address.address");
                Integer addressId = rs.getInt("address.addressId");
                String postalCode = rs.getString("address.postalCode");
                String city = rs.getString("city.city");
                String country = rs.getString("country.country");
                String phone = rs.getString("address.phone");
                customerArray.add(new Customer(id, name, addressId, address, postalCode, city, country, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerArray;
    }
        @Override
    public void initialize(URL location, ResourceBundle resources) {

        appointmentArray.clear();
        customerArray.clear();
        generateAppointmentTable();
        generateCustomerTable();



        }
}
