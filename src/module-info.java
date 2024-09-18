module Software2Project {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens model;
    opens view;
    opens database;
    opens language_files;
}