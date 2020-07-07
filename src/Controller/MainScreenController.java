package Controller;

import Database.DBConnection;
import Model.Appointment;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {


    String startMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    String endMonth = LocalDateTime.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

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
    private RadioButton radioBtnViewWeek;

    @FXML
    private ToggleGroup viewBy;

    @FXML
    private RadioButton radioBtnViewMonth;

    @FXML
    private RadioButton radioBtnViewAll;

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

                    allAppointments.clear();
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

                    allCustomers.clear();
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

    @FXML
    void viewAllHandler(ActionEvent event) {

    }

    @FXML
    void viewMonthHandler(ActionEvent event) {

    }

    @FXML
    void viewWeekHandler(ActionEvent event) {

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
    public static ObservableList<Appointment> queryAppointments() {
        String query = "SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.type, appointment.start, appointment.end FROM appointment";
        try {
            ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("appointment.appointmentID");
                int customerId = rs.getInt("appointment.customerID");
                String title = rs.getString("appointment.title");
                String type = rs.getString("appointment.type");
                LocalDateTime start = rs.getTimestamp("appointment.start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("appointment.end").toLocalDateTime();
                allAppointments.add(new Appointment(id, customerId, title, type, start, end));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
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
                allCustomers.add(new Customer(id, name, addressId, address, postalCode, city, country, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCustomers;
    }
        @Override
    public void initialize(URL location, ResourceBundle resources) {

        allAppointments.clear();
        allCustomers.clear();
        generateAppointmentTable();
        generateCustomerTable();
        /*DataGenerator data = new DataGenerator();
        data.populateAllTables();*/

        }
}
