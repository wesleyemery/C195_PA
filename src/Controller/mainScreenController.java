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
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, String> apptStartCol;

    @FXML
    private TableColumn<Appointment, String> apptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerCol;

    @FXML
    private Button newAppointmentButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> custAddressCol;

    @FXML
    private TableColumn<Customer, String> custCountryCol;

    @FXML
    private TableColumn<Customer, String> custCityCol;

    @FXML
    private TableColumn<Customer, String> custPhoneCol;
   /* @FXML
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
    private TableColumn<Customer, String> customerPhoneColumn;*/

    @FXML
    private Button newCustomerButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private RadioButton radioWeek;

    @FXML
    private ToggleGroup dateSelect;

    @FXML
    private RadioButton radioMonth;

    @FXML
    private RadioButton radioAll;

    @FXML
    private Button customerReportButton;

    @FXML
    private Button scheduleReportButton;

    @FXML
    private Button appointmentReportButton;

    @FXML
    void appointmentReportButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/appointmentReportScreen.fxml"));
        appointmentReportController controller = new appointmentReportController();
        Stage stage = (Stage) appointmentReportButton.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void customerReportButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/customerReportScreen.fxml"));
        customerReportController controller = new customerReportController();
        Stage stage = (Stage) customerReportButton.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void scheduleReportButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/scheduleReportScreen.fxml"));
        scheduleReportController controller = new scheduleReportController();
        Stage stage = (Stage) scheduleReportButton.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void deleteAppointmentAction(ActionEvent event) {
        Appointment appointmentDelete = apptTable.getSelectionModel().getSelectedItem();
        if(appointmentDelete != null) {
            String message = "Are you sure you want to delete " + apptTable.getSelectionModel().getSelectedItem().getTitle() + " appointment?";
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
                    apptTable.setItems(queryAppointments());
                    apptTable.refresh();

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
    void deleteCustomerAction(ActionEvent event) {
        Customer customerDelete = custTable.getSelectionModel().getSelectedItem();
        if(customerDelete != null) {
            String message = "Are you sure you want to delete " + custTable.getSelectionModel().getSelectedItem().getCustomerName() + "?";
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
                    custTable.setItems(queryCustomers());
                    custTable.refresh();

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
    void newAppointmentAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addAppointmentScreen.fxml"));
        addAppointmentController controller = new addAppointmentController();
        Stage stage = (Stage) newAppointmentButton.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void newCustomerAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/addCustomerScreen.fxml"));
        addCustomerController controller = new addCustomerController();
        Stage stage = (Stage) newCustomerButton.getScene().getWindow();

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void updateAppointmentAction(ActionEvent event) throws IOException {

        Appointment selectedAppointment = apptTable.getSelectionModel().getSelectedItem();
        //Address address = address.getAddress();
        if (!(selectedAppointment == null)){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/updateAppointmentScreen.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                updateAppointmentController controller = loader.getController();
                controller.populateAppointmentFields(selectedAppointment);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            return;
    }

    @FXML
    void updateCustomerAction(ActionEvent event) throws IOException {
        Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
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
        apptTable.getItems().setAll(queryAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

        public void generateCustomerTable(){
        custTable.getItems().setAll(queryCustomers());
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custCityCol.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

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
                    String start = rs.getTimestamp("start").toLocalDateTime().toString();
                    String end = rs.getTimestamp("end").toLocalDateTime().toString();
                    appointmentArray.add(new Appointment(id, customerId, title, type, start, end));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return appointmentArray;
        } else if(radioMonth.isSelected()){
            appointmentArray.clear();
            String query = "SELECT appointmentId, customerId, title, type, start, end FROM appointment WHERE appointment.start >= '" + Utils.Time.getMonth() + "' AND appointment.end <= '" + Utils.Time.getEndOfMonth() + "' AND userId='1';";
            //System.out.println(query);
            try (PreparedStatement sm = DBConnection.getConnection().prepareStatement(query);
                 ResultSet rs = sm.executeQuery()) {
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
            try (PreparedStatement sm = DBConnection.getConnection().prepareStatement(query);
                 ResultSet rs = sm.executeQuery()) {
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