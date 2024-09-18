package model;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {



    /** Sets Scene and configures ResourceBundle. */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent main = null;
        //Resource Bundle
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        loader.setResources(rb);
        main = loader.load();
        Scene scene = new Scene(main);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Main method. */
    public static void main(String[] args) {


        //Locale.setDefault(new Locale("fr"));
        // Connects to the database
        Database.connect();

        launch(args);

        // Disconnects from the database
        Database.disconnect();

    }
}
