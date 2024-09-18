package view;

import database.Database;
import database.Log;
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
import model.User;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {

    /** Configures the login form. */
    @FXML
    private Label lblCurrentLocation;
    @FXML
    private Label lblDisplayLocation;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    User currentUser = new User();
    ResourceBundle rb;

    /** Login handler. */
    public void handleLogin (ActionEvent event) throws IOException {
        //Textfield information is stored.
        User.setUsername(txtUsername.getText());
        User.setPassword(txtPassword.getText());

        //Verify password and username
        if(currentUser.Login()) {
            // Checks for upcoming appointments
            Appointment check = Database.checkAppointments();
            Alert alert;
            if(check == null) {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "You have no upcoming appointments.");
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION,
                        "You have an appointment within 15 minutes:\n" +
                                "Appointment ID: " + check.getId() + "\n" +
                                "Date and time: " + check.getStart());
            }
            alert.showAndWait();
            //Record Log
            Log.log(User.getUsername(), true);
            //Change Scene
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
        else {
            Log.log(User.getUsername(), false);
            Alert alert = new Alert(Alert.AlertType.WARNING,rb.getString("ErrorLogin"));
            alert.showAndWait();
        }

    }

    /** LoginController initialize method. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Resource Bundle
        this.rb = rb;
        TimeZone zone = TimeZone.getDefault();
        String name = zone.getDisplayName();
        lblDisplayLocation.setText(name);

        lblCurrentLocation.setText(rb.getString("CurrentLocation"));
        lblWelcome.setText(rb.getString("Welcome"));
        lblUsername.setText(rb.getString("Username"));
        lblPassword.setText(rb.getString("Password"));
        btnLogin.setText(rb.getString("Login"));

    }
}
