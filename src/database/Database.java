package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Database {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/appointment_system?autoReconnect=true";
    private static final String user = "root";
    private static final String password = "";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;


    /**
     * Connects to the database.
     */
    public static void connect() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    /**
     * Disconnects from the database.
     */
    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from database.");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    /**
     * Returns the Connection object.
     *
     * @return returns Connection object
     */
    public static Connection getConnection() {
        return conn;
    }

    /** This method returns an ObservableList with Customer Appointment objects. */
    public static String getTotalByType() {
        try {
            LocalDate startOfMonth = LocalDate.now();
            LocalDate endOfMonth = LocalDate.now().plusMonths(1);
            StringBuilder s = new StringBuilder();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery =
                    "SELECT type, COUNT(*) AS `num` FROM appointments WHERE start >= '" + startOfMonth + "' AND Start <= '" + endOfMonth + "' GROUP BY type ";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                s.append(rs.getString("type")).append("               ").append(rs.getString("num")).append("\n");
            }
            return s.toString();
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
        return null;
    }


    /**
     * This method returns and ObservableList with Customer Objects.
     */
    public static ObservableList<Customer> getCustomerRecords() {
        try {
            ObservableList<Customer> cusList = FXCollections.observableArrayList();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM customers";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Customer temp = new Customer(rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        utcToLocal(rs.getString("Create_Date")),
                        rs.getString("Created_By"),
                        utcToLocal(rs.getString("Last_Update")),
                        rs.getString("Last_Updated_By"),
                        rs.getString("Division_ID"));
                cusList.add(temp);
            }
            return cusList;
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
            return null;
        }
    }

    /** This method returns an ObservableList with Customer Appointment objects. */
    public static ObservableList<Appointment> getAppointments() {
        try {
            ObservableList<Appointment> appointmentList= FXCollections.observableArrayList();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM appointments";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        utcToLocal(rs.getString("Start")),
                        utcToLocal(rs.getString("End")),
                        utcToLocal(rs.getString("Create_Date")),
                        rs.getString("Created_By"),
                        utcToLocal(rs.getString("Last_Update")),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointmentList.add(appointment);
            }
            return appointmentList;
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
        return null;
    }
    /** This method returns an ObservableList with Customer Appointment objects by week. */
    public static ObservableList<Appointment> getAppointmentsByWeek() {
        try {
            LocalDate startOfWeek = LocalDate.now();
            LocalDate endOfWeek = LocalDate.now().plusWeeks(1);
            ObservableList<Appointment> appointmentList= FXCollections.observableArrayList();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery =
                    "SELECT * FROM appointments WHERE start >= '" + startOfWeek + "' AND " +
                     "Start <= '" + endOfWeek + "'";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        utcToLocal(rs.getString("Start")),
                        utcToLocal(rs.getString("End")),
                        utcToLocal(rs.getString("Create_Date")),
                        rs.getString("Created_By"),
                        utcToLocal(rs.getString("Last_Update")),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointmentList.add(appointment);
            }
            return appointmentList;
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
        return null;

    }
    /** This method returns an ObservableList with Customer Appointment objects by Month. */
    public static ObservableList<Appointment> getAppointmentsByMonth() {
        try {
            LocalDate startOfMonth = LocalDate.now();
            LocalDate endOfMonth = LocalDate.now().plusMonths(1);
            ObservableList<Appointment> appointmentList= FXCollections.observableArrayList();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery =
                    "SELECT * FROM appointments WHERE start >= '" + startOfMonth + "' AND " +
                            "Start <= '" + endOfMonth + "'";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        utcToLocal(rs.getString("Start")),
                        utcToLocal(rs.getString("End")),
                        utcToLocal(rs.getString("Create_Date")),
                        rs.getString("Created_By"),
                        utcToLocal(rs.getString("Last_Update")),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointmentList.add(appointment);
            }
            return appointmentList;
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
        return null;

    }



    /**
     * This method returns an ObservableList with Country Objects and their
     * FirstLevelDivisions.
     */
    public static ObservableList<Country> getCountryRecords() {
        try {
            ObservableList<Country> countryList = FXCollections.observableArrayList();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM countries";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Country tempCountry = new Country(
                        rs.getString("Country_ID"),
                        rs.getString("Country"),
                        rs.getString("Create_Date"),
                        rs.getString("Created_By"),
                        rs.getString("Last_Update"),
                        rs.getString("Last_Updated_By"));
                getFirstLevelDivisionRecords(tempCountry);
                countryList.add(tempCountry);
            }
            return countryList;
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
            return null;
        }
    }

    /**
     * This method adds FirstLevelDivision records to Country Objects.
     */
    public static void getFirstLevelDivisionRecords(Country country) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + country.getId();
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                FirstLevelDivision tempDivision = new FirstLevelDivision(
                        rs.getString("Division_ID"),
                        rs.getString("Division"),
                        rs.getString("Create_Date"),
                        rs.getString("Created_By"),
                        rs.getString("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getString("COUNTRY_ID"));
                country.getFirstLevelDivisions().add(tempDivision);
            }
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }

    /** This method returns an ObservableList with Contact objects. */
    public static ObservableList<Contact> getContactRecords() {
        try {
            ObservableList<Contact> contactList = FXCollections.observableArrayList();
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM contacts";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Contact tempContact = new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email"));
                contactList.add(tempContact);
            }
            return contactList;
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
            return null;
        }

    }

    /** Updates existing appointment. */
    public static void updateAppointment(int appointmentId, String title, String description,
                                         String location, String type, String start, String end, int userId,
                                         int contactId) throws SQLException{
                String convertedStart = localToUtc(start);
                String convertedEnd = localToUtc(end);
                Statement statement = Database.getConnection().createStatement();
                String sql =
                        "UPDATE appointments " +
                                "SET Title = '" + title + "', " +
                                "Description = '" + description + "'," +
                                "Location = '" + location + "', " +
                                "Type = '" + type + "', " +
                                "Start = '" + convertedStart + "', " +
                                "End = '" + convertedEnd + "', " +
                                "Last_Update = CURRENT_TIMESTAMP, " +
                                "Last_Updated_By = '" + User.getUsername() + "', " +
                                "User_ID = " + userId + ", " +
                                "Contact_ID = " + contactId + " " +
                                "WHERE Appointment_ID = " + appointmentId;
                statement.execute(sql);
                System.out.println("Update Successful");


    }

    /**
     * Updates existing customers.
     */
    public static void updateCustomer( int id, String name, String address, String postalCode, String phone, int fld) throws SQLException {
            Statement statement = Database.getConnection().createStatement();
            String sql =
                    "UPDATE customers " +
                            "SET Customer_Name = '" + name + "', " +
                            "Address = '" + address + "'," +
                            "Postal_Code = '" + postalCode + "', " +
                            "Phone = '" + phone + "', " +
                            "Last_Update = CURRENT_TIMESTAMP, " +
                            "Last_Updated_By = '" + User.getUsername() + "', " +
                            "Division_ID = '" + fld + "' " +
                            "WHERE Customer_ID = " + id;
            statement.execute(sql);
            System.out.println("Update Successful");

    }

    /**
     * Deletes a customer from the database.
     */
    public static boolean deleteCustomer(int customerId) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String sql =
                    "DELETE FROM customers " +
                            "WHERE Customer_ID = " + customerId;
            statement.execute(sql);
            System.out.println("Customer Delete Successful");
            return true;
        //}catch(SQLIntegrityConstraintViolationException sqlException) {
        }catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
            return false;
       }

    }

    /** Deletes an appointment from the appointments table. */
    public static void deleteAppointment(int appointmentID) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String sql =
                    "DELETE FROM appointments " +
                            "WHERE Appointment_ID = " + appointmentID;
            statement.execute(sql);
            System.out.println("Appointment Delete Successful");
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }
    }

    /** Saves new customer to the customer table. */
    public static void addCustomer( String name, String address, String postalCode, String phone, int divisionId) throws SQLException {
            Statement statement = Database.getConnection().createStatement();
            String sql =
                    "INSERT INTO customers(Customer_Name, Address, Postal_Code," +
                            " Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES('" + name + "', " +
                            "'" + address + "', "+
                            "'" + postalCode + "', " +
                            "'" + phone + "', " +
                            "CURRENT_TIMESTAMP, " +
                            "'" + User.getUsername() + "', " +
                            "CURRENT_TIMESTAMP, " +
                            "'" + User.getUsername() + "', " +
                            divisionId + ")";

            statement.execute(sql);
            System.out.println("Customer Add Successful");


    }

    /** Saves new appointment to the appointment table. */
    public static void addAppointment(String title, String description,
                                      String location, String type, String start,
                                      String end, int customerId, int userId,
                                      int contactId)  throws SQLException {
                String convertedStart = localToUtc(start);
                String convertedEnd = localToUtc(end);
                Statement statement = Database.getConnection().createStatement();
            String sql =
                    "INSERT INTO appointments(Title, Description, " +
                            "Location, Type, Start, End, Create_Date, Created_By, " +
                            "Last_Update, Last_Updated_By, Customer_ID, User_ID, " +
                            "Contact_ID) " +
                            "VALUES('" + title + "', " +
                            "'" + description + "', " +
                            "'" + location + "', " +
                            "'" + type + "', " +
                            "'" + convertedStart + "', " +
                            "'" + convertedEnd + "', " +
                            "CURRENT_TIMESTAMP, " +
                            "'" + User.getUsername() + "', " +
                            "CURRENT_TIMESTAMP, " +
                            "'" + User.getUsername() + "', " +
                            +customerId + ", "
                            + userId + ","
                            + contactId + " )";
            statement.execute(sql);
            System.out.println("Customer Add Successful");


    }

    /** Searches for overlapping appointments by customer id. */
    public static boolean isAppointmentOverlapping(String start, String end, int customerId, ObservableList<Appointment> appointments) throws DateTimeParseException {
        //Parse data
        boolean overlap = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start1 = LocalDateTime.parse(start, formatter);
        LocalDateTime end1 = LocalDateTime.parse(end, formatter);
        for(Appointment a: appointments) {
            if(a.getCustomerId().equals(customerId)) {
                LocalDateTime start2 = LocalDateTime.parse(a.getStart(), formatter);
                LocalDateTime end2 = LocalDateTime.parse(a.getEnd(), formatter);
                if(start1.isBefore(end2) && start2.isBefore(end1))
                    overlap = true;

            }
        }
        return overlap;
    }

    /** Converts UTC to Local time. */
    public static String utcToLocal(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String datetime = date + " " + time;
        //converts data to LocalDate and local time object
        //creates LocalDate object
        LocalDateTime dt = LocalDateTime.parse(datetime,formatter);
        //create assigns sys as timezone
        ZonedDateTime utcDT = dt.atZone(ZoneId.of("UTC"));
        ZonedDateTime sysDT = utcDT.withZoneSameInstant(ZoneId.systemDefault());

        //converts back to localDateTime object
        LocalDateTime parsedDT = sysDT.toLocalDateTime();


        return parsedDT.format(formatter);
    }

    /** Converts Local time to UTC. */
    public static String localToUtc(String dt){
        //est zone
        ZoneId estZoneId = ZoneId.of("America/New_York");
        //Parse data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dt, formatter);
        //create zonedDateTime object and assign it sys default zoneId
        ZonedDateTime zoneDateTime = dateTime.atZone(ZoneId.systemDefault());
        //convert to est
        ZonedDateTime estZonedDateTime = zoneDateTime.withZoneSameInstant(estZoneId);
        //convert to utc
        ZonedDateTime utcZonedDateTime = estZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        //convert back into LocalDateTime
        LocalDateTime time = utcZonedDateTime.toLocalDateTime();


        return time.format(formatter);
    }

    /** Checks if dates entered are within business hours. */
    public static boolean isInBusinessHours(String start, String end) {
        //est zone
        ZoneId estZoneId = ZoneId.of("America/New_York");

        //parse strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        //creates ZonedDateObjects and assigns system time
        ZonedDateTime zoneStart = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime zoneEnd = endDateTime.atZone(ZoneId.systemDefault());
        //Converts to est timezone
        ZonedDateTime estStart = zoneStart.withZoneSameInstant(estZoneId);
        ZonedDateTime estEnd = zoneEnd.withZoneSameInstant(estZoneId);
        //converts back into localtime
        LocalTime startTime = estStart.toLocalTime();
        LocalTime endTime = estEnd.toLocalTime();
        //Business hours
        LocalTime eightEST = LocalTime.of(8, 0);
        LocalTime tenEST = LocalTime.of(22, 0);
        //validates hours
        return (startTime.isAfter(eightEST) || startTime.equals(eightEST)) &&
                (endTime.isBefore(tenEST) || endTime.equals(tenEST) &&
                        startTime.isBefore(endTime));

    }

    /** Checks to see if user has appointments within 15 minutes of logging in. */
    public static Appointment checkAppointments() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime nowPlus15 = LocalDateTime.now().plusMinutes(15);
        ObservableList<Appointment> appointments = Database.getAppointments();
        for(Appointment a: appointments) {
            if(a.getUserId() == User.getId()) {
                LocalDateTime tempStart = LocalDateTime.parse(a.getStart(), formatter);
                if(tempStart.isBefore(nowPlus15) && tempStart.isAfter(LocalDateTime.now())) {
                    return a;
                }
            }
        }
        return null;
    }





}




























