package model;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** User class for User table. */
public class User {
    private static int id;
    private static String username;
    private static String password;

    /** Default constructor. */
    public User() {}

    public User(String username, String password) {
        User.username = username;
        User.password = password;
    }

    /** Login method. */
    public boolean Login() {
        try {
            Statement statement = Database.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM users " +
                              "WHERE User_Name = '" + getUsername() +
                              "' AND Password = '" + getPassword() + "'";
            ResultSet rs = statement.executeQuery(sqlQuery);
            boolean result =  rs.next();
            if(result) {
                setId(rs.getInt("User_ID"));
            }
            return result;
        }
        catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
            return false;
        }
    }

    /** Returns the user Username. */
    public static String getUsername() {
        return username;
    }

    /** Sets the user Username. */
    public static void setUsername(String username) {
        User.username = username;
    }

    /** Returns the user Password. */
    public static String getPassword() {
        return password;
    }

    /** Sets the user Password. */
    public static void setPassword(String password) {
        User.password = password;
    }

    /** Returns the user ID. */
    public static int getId() {
        return id;
    }

    /** Sets the user ID. */
    public static void setId(int id) {
        User.id = id;
    }
}
