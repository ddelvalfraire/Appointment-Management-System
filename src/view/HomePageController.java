package view;

import database.Database;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    //Configures the Customers Tableview
    @FXML
    private TableView<Customer> tvCustomer;
    @FXML
    private TableColumn<Customer, Integer> tcCustomerId;
    @FXML
    private TableColumn<Customer, String> tcCustomerName;
    @FXML
    private TableColumn<Customer, String> tcCustomerAddress;
    @FXML
    private TableColumn<Customer, String> tcCustomerPostalCode;
    @FXML
    private TableColumn<Customer, String> tcCustomerPhone;
    @FXML
    private TableColumn<Customer, String> tcCustomerCreateDate;
    @FXML
    private TableColumn<Customer, String> tcCustomerCreatedBy;
    @FXML
    private TableColumn<Customer, String> tcCustomerLastUpdate;
    @FXML
    private TableColumn<Customer, String> tcCustomerLastUpdateBy;
    @FXML
    private TableColumn<Customer, String> tcCustomerDivisionId;

    @FXML
    private Label lblCustomerId;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblPostalCode;
    @FXML
    private Label lblPhoneNumber;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblFirstLevelDivision;

    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtPostalCode;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private ComboBox<String> cbCountry;
    @FXML
    private ComboBox<String> cbFirstLevelDivision;

    @FXML
    private Button btnNewCustomer;
    @FXML
    private Button btnCustomerUpdate;
    @FXML
    private Button btnCustomerDelete;

    //Configures the Appointments table
    @FXML
    private TableView<Appointment> tvAppointment;
    @FXML
    private TableColumn<Appointment, Integer> tcAppointmentId;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentTitle;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentDescription;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentLocation;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentContact;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentType;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentStart;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentEnd;
    @FXML
    private TableColumn<Appointment, Integer> tcAppointmentCustomerId;

    @FXML
    private Label lblAppointmentId;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblContact;
    @FXML
    private Label lblType;
    @FXML
    private Label lblStart;
    @FXML
    private Label lblEnd;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblUserId;
    @FXML
    private Label lblSelectedCustomer;

    @FXML
    private TextField txtAppointmentId;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtStart;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtUserId;
    @FXML
    private ComboBox<String> cbContact;
    @FXML
    private TextArea txtDescription;

    @FXML
    private RadioButton rbWeek;
    @FXML
    private RadioButton rbMonth;
    @FXML
    private RadioButton rbAll;
    private ToggleGroup tgRadioButtons;


    @FXML
    private Button btnNewAppointment;
    @FXML
    private Button btnAppointmentUpdate;
    @FXML
    private Button btnAppointmentDelete;


    ObservableList<Appointment> appointmentRecords = Database.getAppointments();
    ObservableList<Country> countryRecords = Database.getCountryRecords();
    ObservableList<Contact> contactRecords = Database.getContactRecords();


    /**
     * Sends user to the Reports view.
     */
    public void handleReports(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Reports.fxml"));
        Parent main = loader.load();

        Scene scene = new Scene(main);


        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

    }

    /**
     * Handles sort by month toggle.
     */
    public void handleSortByMonth(ActionEvent event) {
        appointmentRecords = Database.getAppointmentsByMonth();
        tvAppointment.setItems(appointmentRecords);
    }

    /**
     * Handles sort by month toggle.
     */
    public void handleSortByWeek(ActionEvent event) {
        appointmentRecords = Database.getAppointmentsByWeek();
        tvAppointment.setItems(appointmentRecords);
    }

    /**
     * Handles Appointment update.
     */
    public void handleAppointmentUpdate(ActionEvent event) {
        //gets selected appointment
        try {
            if (tvAppointment.getSelectionModel().getSelectedItems().size() > 0) {
                int appointmentId = Integer.parseInt(txtAppointmentId.getText());
                String title = txtTitle.getText();
                String description = txtDescription.getText();
                String location = txtLocation.getText();
                String type = txtType.getText();
                String start = txtStart.getText();
                String end = txtEnd.getText();
                int customerId = tvAppointment.getSelectionModel().getSelectedItem().getCustomerId();
                int userId = Integer.parseInt(txtUserId.getText());
                String contactName = cbContact.getSelectionModel().getSelectedItem();
                int contactId = -1;

                //Find selected contact id
                for (Contact contact : contactRecords)
                    if (contact.getName().equals(contactName))
                        contactId = contact.getId();
                //Checks to see if appointments would overlap
                if (Database.isAppointmentOverlapping(start, end, customerId, appointmentRecords)) {
                    Alert alert5 = new Alert(Alert.AlertType.INFORMATION,
                            "This appointment is overlapping another and can not be created.");
                    alert5.showAndWait();
                    return;
                }
                //Checks to see if hours are in business hours
                if (Database.isInBusinessHours(start, end)) {
                    //Updates the appointment in the database
                    Database.updateAppointment(appointmentId, title, description,
                            location, type, start, end, userId, contactId);
                    //refreshes appointment table
                    setAppointmentTableView();

                } else {
                    Alert alert4 = new Alert(Alert.AlertType.INFORMATION,
                            "Appointment is not between 8AM and 10PM EST.");
                    alert4.showAndWait();

                }
            } else {
                Alert alert4 = new Alert(Alert.AlertType.INFORMATION,
                        "You must select an appointment to update it.");
                alert4.showAndWait();

            }
        } catch(NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "User ID must be a valid integer");
            alert.showAndWait();


        }catch (DateTimeParseException dateTimeParseException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Please enter date and time information in the following format:" +
                            "YYYY-MM-DD HH:MM:SS");
            alert.showAndWait();

        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "You must select a contact to create an appointment");
            alert.showAndWait();

        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }

    /**
     * Handles Appointment deletion
     */
    public void handleAppointmentDelete(ActionEvent event) {

        if (tvAppointment.getSelectionModel().getSelectedItems().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Are you sure you want to delete this appointment?",
                    ButtonType.YES, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Appointment selectedAppointment = tvAppointment.getSelectionModel().getSelectedItem();
                int apptId = selectedAppointment.getId();
                String type = selectedAppointment.getType();
                Database.deleteAppointment(selectedAppointment.getId());
                tvAppointment.getItems().remove(selectedAppointment);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                        "Appointment ID: " + apptId + " Type: " + type + " deleted.");
                alert2.showAndWait();
            }
        } else {
            Alert alert4 = new Alert(Alert.AlertType.INFORMATION,
                    "You must select an appointment to delete.");
            alert4.showAndWait();

        }
    }

    /**
     * Handles Customer deletion.
     */
    public void handleCustomerDelete(ActionEvent event) {

        if (tvCustomer.getSelectionModel().getSelectedItems().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Are you sure you want to delete this customer?",
                    ButtonType.YES, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Customer customer = tvCustomer.getSelectionModel().getSelectedItem();
                String name = customer.getName();
                if (Database.deleteCustomer(customer.getId())) {
                    tvCustomer.getItems().remove(customer);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Customer " + name + " deleted.");
                    alert2.showAndWait();
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.WARNING,
                            "You cannot delete a customer without deleting their appointments first.");
                    alert3.showAndWait();
                }
            }
        } else {
            Alert alert4 = new Alert(Alert.AlertType.INFORMATION,
                    "You must select a customer to delete.");
            alert4.showAndWait();
        }
    }

    /**
     * Handles Customer updates.
     */
    public void handleCustomerUpdate(ActionEvent event) {
        try {
            //Collect textfield and combo box data
            if (tvCustomer.getSelectionModel().getSelectedItems().size() > 0) {
                Customer customer = tvCustomer.getSelectionModel().getSelectedItem();
                int id = customer.getId();
                String name = txtCustomerName.getText();
                String address = txtAddress.getText();
                String postalCode = txtPostalCode.getText();
                String phone = txtPhoneNumber.getText();
                String division = cbFirstLevelDivision.getSelectionModel().getSelectedItem();
                int divisionId = 0;
                for (Country country : countryRecords) {
                    for (FirstLevelDivision fld : country.getFirstLevelDivisions()) {
                        if (fld.getDivision().equals(division)) {
                            divisionId = Integer.parseInt(fld.getId());
                        }
                    }
                }
                //Updates database
                Database.updateCustomer(id, name, address, postalCode, phone, divisionId);
                //Refresh CustomerList
                setCustomerTableView();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "You must select a customer to update.");
                alert.showAndWait();

            }
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "You must select a First-Level Division to update a customer");
            alert.showAndWait();

        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }

    /**
     * Sends the user to the newAppointment scene.
     */
    public void handleNewAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewAppointment.fxml"));
        Parent main = loader.load();

        Scene scene = new Scene(main);

        //Access the controller and call a method
        NewAppointmentController controller = loader.getController();

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    /**
     * Sends the user to the NewCustomer scene.
     */
    public void handleNewCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewCustomer.fxml"));
        Parent main = loader.load();

        Scene scene = new Scene(main);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

    }

    /**
     * Handles selected appointment.
     */
    public void handleAppointmentSelect() {
        Appointment temp = tvAppointment.getSelectionModel().getSelectedItem();
        txtAppointmentId.setText(temp.getId().toString());
        txtTitle.setText(temp.getTitle());
        txtType.setText(temp.getType());
        txtDescription.setText(temp.getDescription());
        txtStart.setText(temp.getStart());
        txtEnd.setText(temp.getEnd());
        txtLocation.setText(temp.getLocation());
        txtUserId.setText(temp.getUserId().toString());

        //sets contact box
        int contactId = temp.getContactId();
        for (Contact contact : contactRecords) {
            if (contact.getId() == contactId)
                cbContact.getSelectionModel().select(contact.getName());
        }

    }


    /**
     * Handles selected customer.
     */
    public void handleCustomerSelect() {
        Customer temp = tvCustomer.getSelectionModel().getSelectedItem();
        txtCustomerId.setText(temp.getId().toString());
        txtCustomerName.setText(temp.getName());
        txtAddress.setText(temp.getAddress());
        txtPostalCode.setText(temp.getPostalCode());
        txtPhoneNumber.setText(temp.getPhone());

        //sets CountryCombobox
        String divisionId = temp.getDivisionId();
        for (Country country : countryRecords) {
            for (FirstLevelDivision fld : country.getFirstLevelDivisions()) {
                if (fld.getId().equals(divisionId)) {
                    cbCountry.getSelectionModel().select(country.getName());
                    cbFirstLevelDivision.getSelectionModel().select(fld.getDivision());
                }
            }
        }

    }

    /**
     * Handles selection in the cbCountry combobox.
     */
    public void handleCountrySelect() {
        cbFirstLevelDivision.getItems().clear();
        String countryName = cbCountry.getSelectionModel().getSelectedItem();
        for (Country country : countryRecords) {
            if (country.getName().equals(countryName)) {
                for (FirstLevelDivision division : country.getFirstLevelDivisions()) {
                    cbFirstLevelDivision.getItems().add(division.getDivision());

                }
                return;
            }
        }

    }

    /** HomePageController initialize method

     The first lambda expression detects the selection of an
     appointment in tvAppointment and then utilizes the
     handleAppointmentSelect method

     The second lambda expression detects the selection of a
     customer in tvCustomer and then utilizes the
     handleCustomerSelect method

     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tgRadioButtons = new ToggleGroup();
        rbWeek.setToggleGroup(tgRadioButtons);
        rbMonth.setToggleGroup(tgRadioButtons);
        rbAll.setToggleGroup(tgRadioButtons);



        tvAppointment.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleAppointmentSelect();
            }
        });


        tvCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleCustomerSelect();
            }
        });
        //Configure the contact combo box
        for (Contact contact : contactRecords) {
            cbContact.getItems().add(contact.getName());
        }
        //Configure the country combo box
        for (Country country : countryRecords) {
            cbCountry.getItems().add(country.getName());
        }
        //Configure the Customer table view
        setCustomerTableView();

        tcCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tcCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tcCustomerCreateDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        tcCustomerCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        tcCustomerLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        tcCustomerLastUpdateBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        tcCustomerDivisionId.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        tvCustomer.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        txtCustomerId.setEditable(false);

        //Configure the Appointment table view
        tvAppointment.setItems(appointmentRecords);
        tcAppointmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcAppointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcAppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcAppointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tcAppointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        tcAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcAppointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        tcAppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        tcAppointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        tvAppointment.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        txtAppointmentId.setEditable(false);

    }

    /**
     * Configure customer tableview.
     */
    public void setCustomerTableView() {
        ObservableList<Customer> customerRecords = Database.getCustomerRecords();
        tvCustomer.setItems(customerRecords);

    }

    /**
     * Configure appointment tableview.
     */
    public void setAppointmentTableView() {
        ObservableList<Appointment> appointmentRecords = Database.getAppointments();
        tvAppointment.setItems(appointmentRecords);
    }

}
