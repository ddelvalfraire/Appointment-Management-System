package view;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;

public class ReportsController {

    @FXML
    private Label lblTableView;
    @FXML
    private Label lblTypeAndMonthReport;

    @FXML
    private TableView<Appointment> tvAppointment;
    @FXML
    private TableColumn<Appointment, Integer> tcAppointmentId;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentTitle;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentDescription;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentType;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentStart;
    @FXML
    private TableColumn<Appointment, String> tcAppointmentEnd;
    @FXML
    private TableColumn<Appointment, Integer> tcAppointmentCustomerId;

    @FXML
    private ComboBox<String> cbContact;
    @FXML
    private ComboBox<String> cbCustomer;


    ObservableList<Contact> contactRecords = Database.getContactRecords();
    ObservableList<Customer> customerRecords = Database.getCustomerRecords();
    ObservableList<Appointment> appointmentRecords = Database.getAppointments();
    ObservableList<Appointment> emptyList = FXCollections.observableArrayList();


    /** Sends customer back to the HomePage. */
    public void toHomepage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/HomePage.fxml"));
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

    /** Handle customer select. */
    public void appointmentsByCustomer(ActionEvent event) {
        ObservableList<Appointment> tempAppointmentRecords = FXCollections.observableArrayList();
        String name = cbCustomer.getSelectionModel().getSelectedItem();
        int id = 0;
        for(Customer customer: customerRecords) {
            if(customer.getName().equals(name)) {
                id = customer.getId();
            }
        }
        for(Appointment appointment: appointmentRecords) {
            if(appointment.getCustomerId() == id) {
                tempAppointmentRecords.add(appointment);
            }
        }
        tvAppointment.setItems(tempAppointmentRecords);
        lblTableView.setText("Showing schedule for customer: " + name);
    }
    /** Handle contact select. */
    public void appointmentsByContact(ActionEvent event) {
        ObservableList<Appointment> tempAppointmentRecords = FXCollections.observableArrayList();
        String name = cbContact.getSelectionModel().getSelectedItem();
        int id = 0;
        for(Contact contact: contactRecords) {
            if(contact.getName().equals(name)) {
                id = contact.getId();
            }
        }
        for(Appointment appointment: appointmentRecords) {
            if(appointment.getContactId() == id) {
                tempAppointmentRecords.add(appointment);
            }
        }
        tvAppointment.setItems(tempAppointmentRecords);
        lblTableView.setText("Showing schedule for contact: " + name);
    }


    /** Initialize method for ReportsController. */
    public void initialize() {

        lblTypeAndMonthReport.setText(Database.getTotalByType());
        lblTableView.setText(" ");
        tvAppointment.setItems(emptyList);
        tcAppointmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcAppointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcAppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcAppointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        tcAppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        tcAppointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //Configure the contact combo box
        for (Contact contact : contactRecords) {
            cbContact.getItems().add(contact.getName());
        }
        //Configure the customer combo box
        for (Customer customer: customerRecords) {
            cbCustomer.getItems().add(customer.getName());
        }
    }

}
