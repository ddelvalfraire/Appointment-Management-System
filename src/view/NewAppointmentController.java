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
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {

    @FXML
    private Label lblAppointmentId;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblStart;
    @FXML
    private Label lblEnd;
    @FXML
    private Label lblCustomerId;
    @FXML
    private Label lblUserId;
    @FXML
    private Label lblContact;
    @FXML
    private Label lblType;
    @FXML
    private Label lblDescription;

    @FXML
    private TextField txtAppointmentId;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtStart;
    @FXML
    private TextField txtEnd;
    @FXML
    private ComboBox<String> cbContact;

    ObservableList<Appointment> appointmentRecords = Database.getAppointments();
    ObservableList<Contact> contactRecords = Database.getContactRecords();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Configure the contact combo box
        for (Contact contact : contactRecords) {
            cbContact.getItems().add(contact.getName());
        }
        txtAppointmentId.setEditable(false);

    }

    /**
     * Saves new appointment and sends user to homepage.
     */
    public void handleSaveAppointment(ActionEvent event) throws IOException {
        try {
            String title = txtTitle.getText();
            String location = txtLocation.getText();
            String description = txtDescription.getText();
            String type = txtType.getText();
            int customerId = Integer.parseInt(txtCustomerId.getText());
            int userId = Integer.parseInt(txtUserId.getText());
            String start = txtStart.getText();
            String end = txtEnd.getText();
            String contactName = cbContact.getSelectionModel().getSelectedItem();
            int contactId = 0;
            //Find selected contact id
            for (Contact contact : contactRecords)
                if (contact.getName().equals(contactName))
                    contactId = contact.getId();

            //Checks for overlapping appointments
            if (Database.isAppointmentOverlapping(start, end, customerId, appointmentRecords)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION,
                        "This appointment is overlapping another and can not be created.");
                alert1.showAndWait();
                return;

            }
            //Checks to make sure appointment is in business hours
            if (Database.isInBusinessHours(start, end)) {
                Database.addAppointment(title, description, location, type, start, end, customerId, userId, contactId);
                //Sends user to homepage
                handleCancel(event);


            } else {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                        "This appointment is not between 8AM and 10PM EST");
                alert2.showAndWait();

            }
        } catch(NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "User ID and Customer ID must be a valid integer");
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
     * Sends the user back to the HomePage.
     */
    public void handleCancel(ActionEvent event) throws IOException {
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


}
