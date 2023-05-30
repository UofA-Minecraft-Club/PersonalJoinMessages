package mc.uofa.cat6172.customjoinmessages;

import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SQLiteAccess {
    private Connection connection; // Connection object to interact with the database
    private String tableName; // Name of the table in the database

    public SQLiteAccess(String dbName, String tableName) {
        this.tableName = tableName;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName); // Connect to the database

            // Check if the database file exists
            File dbFile = new File(dbName);
            boolean fileExists = dbFile.exists();

            // If the file doesn't exist, create a new database file
            if (!fileExists) {
                connection.createStatement().execute("CREATE DATABASE " + dbName);
            }

            initializeTable(); // Create the table if it doesn't already exist
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Creates the table if it doesn't already exist
    private void initializeTable() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (name TEXT PRIMARY KEY, login TEXT, quit TEXT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    // Sets the value of a given column for a given key in the database,
    // and deletes the entry from the database if both "join" and "quit" values are null
    private void putValue(String name, String column, String value) throws SQLException {
        if (exists(name)) {
            String updateQuery = "UPDATE " + tableName + " SET " + column + " = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, value);
            statement.setString(2, name);
            statement.executeUpdate();
        } else {
            String insertQuery = "INSERT INTO " + tableName + " (name, " + column + ") VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, name);
            statement.setString(2, value);
            statement.executeUpdate();
        }
    }

    private boolean exists(String name) throws SQLException {
        String selectQuery = "SELECT name FROM " + tableName + " WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    // Sets the "join" value for a given key in the database
    public void putJoin(String name, String join) throws SQLException {
        putValue(name, "login", join);
    }

    // Sets the "quit" value for a given key in the database
    public void putQuit(String name, String quit) throws SQLException {
        putValue(name, "quit", quit);
    }

    // Retrieves the value of a given column for a given key from the database
    private String getValue(String name, String column) throws SQLException {
        String selectQuery = "SELECT " + column + " FROM " + tableName + " WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(column);
        }
        return null;
    }

    // Retrieves the "join" value for a given key from the database
    public String getJoin(String name) throws SQLException {
        return getValue(name, "login");
    }

    // Retrieves the "quit" value for a given key from the database
    public String getQuit(String name) throws SQLException {
        return getValue(name, "quit");
    }

    // Deletes an entry with the given key from the database
    private void delete(String name) throws SQLException {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(deleteQuery);
        statement.setString(1, name);
        statement.executeUpdate();
    }

    public Set<String> getAllKeys() throws SQLException {
        Set<String> keys = new HashSet<>();
        String selectQuery = "SELECT name FROM " + tableName;
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString("name");
            keys.add(key);
        }
        return keys;
    }

    public Set<String> getKeysWithNonNullJoin() throws SQLException {
        Set<String> keys = new HashSet<>();
        String selectQuery = "SELECT name FROM " + tableName + " WHERE login IS NOT NULL";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString("name");
            keys.add(key);
        }
        return keys;
    }

    public Set<String> getKeysWithNonNullQuit() throws SQLException {
        Set<String> keys = new HashSet<>();
        String selectQuery = "SELECT name FROM " + tableName + " WHERE quit IS NOT NULL";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString("name");
            keys.add(key);
        }
        return keys;
    }
}