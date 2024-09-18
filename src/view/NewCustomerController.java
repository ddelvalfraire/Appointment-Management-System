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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    @FXML private Label lblName;
    @FXML private Label lblAddress;
    @FXML private Label lblPostalCode;
    @FXML private Label lblPhone;
    @FXML private Label lblCountry;
    @FXML private Label lblFirstLevelDivision;

    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPostalCode;
    @FXML private TextField txtPhoneNumber;

    @FXML private ComboBox<String> cbCountry;
    @FXML private ComboBox<String> cbFirstLevelDivision;

    ResourceBundle rb;
    ObservableList<Country> countryRecords = Database.getCountryRecords();


    /** Loads the appropriate divisions based on what is selected in the
        Country combo box. */
    public void handleCountrySelect() {
        cbFirstLevelDivision.getItems().clear();
        String countryName = cbCountry.getSelectionModel().getSelectedItem();
        for(Country country: countryRecords) {
            if(country.getName().equals(countryName)) {
                for(FirstLevelDivision division: country.getFirstLevelDivisions()) {
                    cbFirstLevelDivision.getItems().add(division.getDivision());

                }
                return;
            }
        }

    }

    /** Sends the user back to the HomePage. */
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

    /** Saves new customer to database. */
    public void handleSaveNewCustomer(ActionEvent event) throws IOException {
        try {

            int divisionId = 0;
            String name = txtName.getText();
            String address = txtAddress.getText();
            String postalCode = txtPostalCode.getText();
            String phone = txtPhoneNumber.getText();
            String division = cbFirstLevelDivision.getSelectionModel().getSelectedItem();

            //Gets the division id
            for (Country country : countryRecords) {
                for (FirstLevelDivision fld : country.getFirstLevelDivisions()) {
                    if (fld.getDivision().equals(division)) {
                        divisionId = Integer.parseInt(fld.getId());
                    }
                }
            }
            Database.addCustomer(name, address, postalCode, phone, divisionId);
            handleCancel(event);

        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "You must select a First-Level Division to create a customer");
            alert.showAndWait();

        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }


    /** Initialize method for the NewCustomerController. */
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;

        //Configure the country combo box
        for(Country country: countryRecords) {
            cbCountry.getItems().add(country.getName());
        }

    }

}
